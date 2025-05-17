package com.netmap.netmapservice.domain.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private List<String> skills;
}

