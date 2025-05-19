package com.netmap.netmapservice.service;

import com.netmap.netmapservice.domain.request.CreateJobRequest;
import com.netmap.netmapservice.domain.request.FilterJobRequest;
import com.netmap.netmapservice.domain.response.JobPostResponse;
import com.netmap.netmapservice.domain.response.JobSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface JobService {
    JobPostResponse createJob(UUID employerId, CreateJobRequest request);
    List<JobPostResponse> getJobsForUser(UUID userId);

    List<JobPostResponse> getUnverifiedJobs();
    void verifyJobPosting(UUID jobId);

    List<JobSummaryResponse> filterJobs(FilterJobRequest request);

}
