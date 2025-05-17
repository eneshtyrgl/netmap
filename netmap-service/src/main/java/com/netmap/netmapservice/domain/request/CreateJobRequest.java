package com.netmap.netmapservice.domain.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateJobRequest {
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private Integer salary;
    private Boolean isRemote;
    private Boolean isFreelance;
    private List<String> requiredSkills;
}

