package com.netmap.netmapservice.domain.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class JobPostResponse {
    private UUID id;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private Integer salary;
    private Boolean isRemote;
    private Boolean isFreelance;
    private String companyName;
    private List<String> requiredSkills;
}
