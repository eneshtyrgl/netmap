package com.netmap.netmapservice.domain.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FilterJobRequest {
    private Boolean isRemote;
    private Boolean isFreelance;
    private List<String> skills;
    private BigDecimal userLatitude;
    private BigDecimal userLongitude;
    private Double radiusKm;
}
