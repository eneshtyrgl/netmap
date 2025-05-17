package com.netmap.netmapservice.domain.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateJobRequest {
    private String title;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer salary;
    private Boolean isRemote;
    private Boolean isFreelance;
    private List<String> skills;
}
