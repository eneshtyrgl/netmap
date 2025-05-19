package com.netmap.netmapservice.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

@AllArgsConstructor
@Data
public class JobSummaryResponse {
    private UUID id;
    private String title;
    private String companyName;
    private Integer salary;
    private Boolean isRemote;
    private Boolean isFreelance;
    private double latitude;
    private double longitude;
    private List<String> skills;
    private String description;
    private LocalDate postDate;
}
