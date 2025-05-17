package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.request.LoginRequest;
import com.netmap.netmapservice.domain.request.RegisterRequest;
import com.netmap.netmapservice.domain.response.AuthResponse;
import com.netmap.netmapservice.model.AppUser;
import com.netmap.netmapservice.repository.AppUserRepository;
import com.netmap.netmapservice.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AppUserRepository appUserRepository,
                           PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        AppUser newUser = new AppUser();
        newUser.setUsername(request.getUsername());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setBirthDate(request.getBirthDate());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(AppUser.Role.valueOf(request.getRole()));

        appUserRepository.save(newUser);

        // Temporary token since JWT is not yet implemented
        String token = "mock-token";
        return new AuthResponse(token, newUser.getRole(), newUser.getFirstName(), newUser.getLastName());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Optional<AppUser> userOpt = appUserRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        AppUser user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Temporary token since JWT is not yet implemented
        String token = "mock-token";
        return new AuthResponse(token, user.getRole(), user.getFirstName(), user.getLastName());
    }
}
