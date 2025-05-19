package com.netmap.netmapservice.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class JobSummaryResponse {
    private UUID id;
    private String title;
    private String companyName;
    private Integer salary;
    private Boolean isRemote;
    private Boolean isFreelance;
    private double latitude;
    private double longitude;
}

