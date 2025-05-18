package com.netmap.netmapservice.domain.request;

import com.netmap.netmapservice.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    @NotNull
    private LocalDate birthDate;
}
