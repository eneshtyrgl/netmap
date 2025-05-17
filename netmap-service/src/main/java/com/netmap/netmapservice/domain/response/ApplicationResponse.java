package com.netmap.netmapservice.domain.response;

import com.netmap.netmapservice.model.JobApplication;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ApplicationResponse {
    private UUID applicationId;
    private UUID jobPostingId;
    private String jobTitle;
    private LocalDateTime applicationDate;
    private String status;

    public ApplicationResponse(JobApplication application) {
        this.applicationId = application.getId();
        this.jobPostingId = application.getJobPosting().getId();
        this.jobTitle = application.getJobPosting().getTitle();
        this.applicationDate = application.getApplicationDate();
        this.status = application.getStatus().name();
    }
}
