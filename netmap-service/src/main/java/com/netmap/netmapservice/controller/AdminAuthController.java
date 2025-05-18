package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.domain.request.LoginRequest;
import com.netmap.netmapservice.domain.response.AuthResponse;
import com.netmap.netmapservice.service.AdminAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(adminAuthService.login(request));
    }
}
