package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.response.ApplicationResponse;
import com.netmap.netmapservice.model.*;
import com.netmap.netmapservice.repository.JobApplicationRepository;
import com.netmap.netmapservice.repository.JobPostingRepository;
import com.netmap.netmapservice.repository.JobSeekerRepository;
import com.netmap.netmapservice.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final JobSeekerRepository jobSeekerRepository;

    @Override
    @Transactional
    public void applyToJob(UUID jobSeekerId, UUID jobPostingId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new RuntimeException("Job posting not found"));

        JobApplication application = new JobApplication();
        application.setJobSeeker(jobSeeker);
        application.setJobPosting(jobPosting);
        application.setApplicationDate(LocalDateTime.now());
        application.setStatus(ApplicationStatus.PENDING);

        jobApplicationRepository.save(application);
    }

    @Override
    public List<ApplicationResponse> getApplicationsByJobSeeker(UUID appUserId) {
        JobSeeker jobSeeker = jobSeekerRepository.findByAppUserId(appUserId)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        return jobApplicationRepository.findByJobSeeker(jobSeeker).stream()
                .map(ApplicationResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponse> getApplicationsByJobPosting(UUID jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new RuntimeException("Job posting not found"));

        return jobApplicationRepository.findByJobPosting(jobPosting).stream()
                .map(ApplicationResponse::new)
                .collect(Collectors.toList());
    }
}
