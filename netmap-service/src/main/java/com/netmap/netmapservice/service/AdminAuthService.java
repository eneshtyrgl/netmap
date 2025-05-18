package com.netmap.netmapservice.service;

import com.netmap.netmapservice.domain.request.LoginRequest;
import com.netmap.netmapservice.domain.response.AuthResponse;

public interface AdminAuthService {
    AuthResponse login(LoginRequest request);
}
