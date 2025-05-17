package com.netmap.netmapservice.service;

import com.netmap.netmapservice.domain.request.UpdateProfileRequest;
import com.netmap.netmapservice.domain.response.ProfileResponse;

import java.util.UUID;

public interface UserService {
    ProfileResponse getProfile(UUID userId);
    void updateProfile(UUID userId, UpdateProfileRequest request);
}
