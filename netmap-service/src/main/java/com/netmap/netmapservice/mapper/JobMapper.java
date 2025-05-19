package com.netmap.netmapservice.mapper;

import com.netmap.netmapservice.domain.response.JobSummaryResponse;
import com.netmap.netmapservice.model.JobPosting;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public JobSummaryResponse toSummary(JobPosting job) {
        return new JobSummaryResponse(
                job.getId(),
                job.getTitle(),
                job.getEmployer().getCompany() != null
                        ? job.getEmployer().getCompany().getName()
                        : "Unknown Company",
                job.getSalary(),
                job.getIsRemote(),
                job.getIsFreelance(),
                job.getLatitude().doubleValue(),
                job.getLongitude().doubleValue()
        );
    }
}
