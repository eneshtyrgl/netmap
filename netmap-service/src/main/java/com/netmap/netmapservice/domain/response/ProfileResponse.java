package com.netmap.netmapservice.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class ProfileResponse {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private List<String> skills;
}
