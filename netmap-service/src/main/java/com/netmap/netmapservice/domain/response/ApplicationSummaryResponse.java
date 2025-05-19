package com.netmap.netmapservice.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ApplicationSummaryResponse {
    private UUID applicationId;
    private UUID jobPostingId;
    private String jobTitle;
    private String companyName;
    private String status;
    private LocalDateTime applicationDate;
}
