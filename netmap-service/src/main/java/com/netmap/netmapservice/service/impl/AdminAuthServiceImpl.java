package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.request.LoginRequest;
import com.netmap.netmapservice.domain.response.AuthResponse;
import com.netmap.netmapservice.model.Administrator;
import com.netmap.netmapservice.repository.AdministratorRepository;
import com.netmap.netmapservice.security.JwtUtil;
import com.netmap.netmapservice.service.AdminAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        Administrator admin = administratorRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(admin.getId(), "ADMIN");


        return new AuthResponse(
                token,
                admin.getId(),
                "ADMIN",
                "Admin",
                "User",
                admin.getUsername()
        );
    }
}
