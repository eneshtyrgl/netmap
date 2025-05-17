package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.response.ApplicationResponse;
import com.netmap.netmapservice.model.JobApplication;
import com.netmap.netmapservice.model.JobPosting;
import com.netmap.netmapservice.model.JobSeeker;
import com.netmap.netmapservice.repository.JobApplicationRepository;
import com.netmap.netmapservice.repository.JobPostingRepository;
import com.netmap.netmapservice.repository.JobSeekerRepository;
import com.netmap.netmapservice.service.ApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final JobPostingRepository jobPostingRepository;

    public ApplicationServiceImpl(JobApplicationRepository jobApplicationRepository,
                                  JobSeekerRepository jobSeekerRepository,
                                  JobPostingRepository jobPostingRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.jobPostingRepository = jobPostingRepository;
    }

    @Override
    public void applyToJob(UUID jobSeekerId, UUID jobPostingId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new RuntimeException("Job posting not found"));

        boolean alreadyApplied = jobApplicationRepository.existsByJobSeekerAndJobPosting(jobSeeker, jobPosting);
        if (alreadyApplied) {
            throw new RuntimeException("Already applied to this job");
        }

        JobApplication application = new JobApplication();
        application.setJobSeeker(jobSeeker);
        application.setJobPosting(jobPosting);
        jobApplicationRepository.save(application);
    }

    @Override
    public List<ApplicationResponse> getApplicationsByJobSeeker(UUID jobSeekerId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

        return jobApplicationRepository.findByJobSeeker(jobSeeker).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponse> getApplicationsByJobPosting(UUID jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new RuntimeException("Job posting not found"));

        return jobApplicationRepository.findByJobPosting(jobPosting).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ApplicationResponse mapToResponse(JobApplication application) {
        return new ApplicationResponse(application);
    }

}
