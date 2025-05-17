package com.netmap.netmapservice.service;

import com.netmap.netmapservice.domain.response.ApplicationResponse;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    void applyToJob(UUID jobSeekerId, UUID jobPostingId);
    List<ApplicationResponse> getApplicationsByJobSeeker(UUID jobSeekerId);
    List<ApplicationResponse> getApplicationsByJobPosting(UUID jobPostingId);
}
