package com.netmap.netmapservice.service;

import com.netmap.netmapservice.domain.response.ApplicationResponse;
import com.netmap.netmapservice.domain.response.ApplicationSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    boolean applyToJob(UUID jobSeekerId, UUID jobPostingId);
    List<ApplicationResponse> getApplicationsByJobSeeker(UUID jobSeekerId);
    List<ApplicationResponse> getApplicationsByJobPosting(UUID jobPostingId);
    List<ApplicationSummaryResponse> getApplicationSummariesByUser(UUID appUserId);
}
