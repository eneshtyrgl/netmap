package com.netmap.netmapservice.domain.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ApplicationResponse {
    private UUID applicationId;
    private UUID jobId;
    private String jobTitle;
    private String companyName;
    private String status;
}
