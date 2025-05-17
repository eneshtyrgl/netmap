package com.netmap.netmapservice.service;

import com.netmap.netmapservice.domain.request.LoginRequest;
import com.netmap.netmapservice.domain.request.RegisterRequest;
import com.netmap.netmapservice.domain.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
