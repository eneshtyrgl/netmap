package com.netmap.netmapservice.security;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomPrincipal {
    private final UUID userId;
    private final String role;

    public CustomPrincipal(UUID userId, String role) {
        this.userId = userId;
        this.role = role;
    }
}
