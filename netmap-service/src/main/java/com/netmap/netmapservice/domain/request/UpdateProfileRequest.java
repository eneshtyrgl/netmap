package com.netmap.netmapservice.domain.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private List<String> skills;
    private Integer experienceYears;
    private String educationLevel;
}
