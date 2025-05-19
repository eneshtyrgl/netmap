package com.netmap.netmapservice.mapper;

import com.netmap.netmapservice.domain.response.ApplicationSummaryResponse;
import com.netmap.netmapservice.model.JobApplication;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationSummaryResponse toSummary(JobApplication app) {
        return new ApplicationSummaryResponse(
                app.getId(),
                app.getJobPosting().getId(),
                app.getJobPosting().getTitle(),
                app.getJobPosting().getEmployer().getCompany() != null
                        ? app.getJobPosting().getEmployer().getCompany().getName()
                        : "Unknown Company",
                app.getStatus().name(),
                app.getApplicationDate()
        );
    }
}
