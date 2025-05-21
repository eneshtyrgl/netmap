package com.netmap.netmapservice.domain.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class FilterJobRequest {
    private Boolean isRemote;
    private Boolean isFreelance;
    private List<UUID> skills;
    private BigDecimal userLatitude;
    private BigDecimal userLongitude;
    private Double radiusKm;
}
