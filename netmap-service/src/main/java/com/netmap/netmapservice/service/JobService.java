package com.netmap.netmapservice.service;

import com.netmap.netmapservice.domain.request.CreateJobRequest;
import com.netmap.netmapservice.domain.response.JobPostResponse;

import java.util.List;
import java.util.UUID;

public interface JobService {
    JobPostResponse createJob(UUID employerId, CreateJobRequest request);
    List<JobPostResponse> getJobsForUser(UUID userId);

    List<JobPostResponse> getUnverifiedJobs();
    void verifyJobPosting(UUID jobId);
}
