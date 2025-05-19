package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.request.LoginRequest;
import com.netmap.netmapservice.domain.request.RegisterRequest;
import com.netmap.netmapservice.domain.response.AuthResponse;
import com.netmap.netmapservice.model.*;
import com.netmap.netmapservice.repository.AppUserRepository;
import com.netmap.netmapservice.repository.EmployerRepository;
import com.netmap.netmapservice.repository.JobSeekerRepository;
import com.netmap.netmapservice.security.JwtUtil;
import com.netmap.netmapservice.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final EmployerRepository employerRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AppUserRepository appUserRepository,
                           EmployerRepository employerRepository,
                           JobSeekerRepository jobSeekerRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.appUserRepository = appUserRepository;
        this.employerRepository = employerRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        AppUser newUser = new AppUser();
        newUser.setUsername(request.getUsername());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setBirthDate(request.getBirthDate());
        newUser.setRegistrationDate(LocalDateTime.now());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(request.getRole());

        AppUser savedUser = appUserRepository.save(newUser);

        switch (request.getRole()) {
            case EMPLOYER -> {
                Employer employer = Employer.builder()
                        .appUser(savedUser)
                        .company(null)
                        .build();
                employerRepository.save(employer);
            }
            case JOB_SEEKER -> {
                JobSeeker jobSeeker = JobSeeker.builder()
                        .appUser(savedUser)
                        .build();
                jobSeekerRepository.save(jobSeeker);
            }
        }

        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getRole().name());
        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getRole().name(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getUsername()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Optional<AppUser> userOpt = appUserRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + request.getUsername());
        }

        AppUser user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getRole().name());
        return new AuthResponse(
                token,
                user.getId(),
                user.getRole().name(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername()
        );
    }
}
