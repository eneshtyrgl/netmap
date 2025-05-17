package com.netmap.netmapservice.domain.response;

import com.netmap.netmapservice.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private AppUser.Role role;
    private String firstName;
    private String lastName;
}
