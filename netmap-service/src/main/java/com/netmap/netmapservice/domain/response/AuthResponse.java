package com.netmap.netmapservice.domain.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String role;
    private String firstName;
    private String lastName;
}
