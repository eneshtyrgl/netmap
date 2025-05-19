package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.request.CreateJobRequest;
import com.netmap.netmapservice.domain.request.FilterJobRequest;
import com.netmap.netmapservice.domain.response.JobPostResponse;
import com.netmap.netmapservice.domain.response.JobSummaryResponse;
import com.netmap.netmapservice.mapper.JobMapper;
import com.netmap.netmapservice.model.AppUser;
import com.netmap.netmapservice.model.Employer;
import com.netmap.netmapservice.model.JobPosting;
import com.netmap.netmapservice.model.Skill;
import com.netmap.netmapservice.repository.AppUserRepository;
import com.netmap.netmapservice.repository.EmployerRepository;
import com.netmap.netmapservice.repository.JobPostingRepository;
import com.netmap.netmapservice.repository.SkillRepository;
import com.netmap.netmapservice.service.JobService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final AppUserRepository appUserRepository;
    private final EmployerRepository employerRepository;
    private final JobPostingRepository jobPostingRepository;
    private final SkillRepository skillRepository;
    private final JobMapper jobMapper;

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }


    @Autowired
    public JobServiceImpl(AppUserRepository appUserRepository,
                          EmployerRepository employerRepository,
                          JobPostingRepository jobPostingRepository,
                          SkillRepository skillRepository,
                          JobMapper jobMapper) {
        this.appUserRepository = appUserRepository;
        this.employerRepository = employerRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.skillRepository = skillRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    @Transactional
    public JobPostResponse createJob(UUID employerId, CreateJobRequest request) {
        Employer employer = employerRepository.findByAppUserId(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        List<Skill> requiredSkills = skillRepository.findByNameIn(request.getSkills());

        JobPosting jobPosting = JobPosting.builder()
                .employer(employer)
                .title(request.getTitle())
                .description(request.getDescription())
                .postDate(LocalDate.now())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .salary(request.getSalary())
                .isRemote(request.getIsRemote())
                .isFreelance(request.getIsFreelance())
                .verified(false)
                .skills(requiredSkills)
                .build();

        jobPostingRepository.save(jobPosting);

        return new JobPostResponse(jobPosting);
    }

    @Override
    public List<JobPostResponse> getJobsForUser(UUID userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<JobPosting> jobs;

        switch (user.getRole()) {
            case ADMIN -> jobs = jobPostingRepository.findAll();
            case EMPLOYER -> jobs = jobPostingRepository.findByEmployerAppUserId(userId);
            default -> jobs = jobPostingRepository.findByVerifiedTrue();
        }

        return jobs.stream().map(JobPostResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<JobPostResponse> getUnverifiedJobs() {
        return jobPostingRepository.findByVerifiedFalse()
                .stream()
                .map(JobPostResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void verifyJobPosting(UUID jobId) {
        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        job.setVerified(true);
        jobPostingRepository.save(job);
    }

    @Override
    public List<JobSummaryResponse> filterJobs(FilterJobRequest request) {
        List<JobPosting> jobs = jobPostingRepository.findByVerifiedTrue();

        return jobs.stream()
                .filter(job -> request.getIsRemote() == null || job.getIsRemote().equals(request.getIsRemote()))
                .filter(job -> request.getIsFreelance() == null || job.getIsFreelance().equals(request.getIsFreelance()))
                .filter(job -> request.getSkills() == null || request.getSkills().isEmpty() ||
                        job.getSkills().stream().anyMatch(skill -> request.getSkills().contains(skill.getName())))
                .map(jobMapper::toSummary)
                .filter(summary -> {
                    if (request.getRadiusKm() == null ||
                            request.getUserLatitude() == null ||
                            request.getUserLongitude() == null) {
                        return true;
                    }
                    return haversine(
                            request.getUserLatitude().doubleValue(),
                            request.getUserLongitude().doubleValue(),
                            summary.getLatitude(),
                            summary.getLongitude()
                    ) <= request.getRadiusKm();
                })
                .collect(Collectors.toList());
    }
}
