package com.netmap.netmapservice.domain.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role; // "JOB_SEEKER" or "EMPLOYER"
    private LocalDate birthDate;
}

