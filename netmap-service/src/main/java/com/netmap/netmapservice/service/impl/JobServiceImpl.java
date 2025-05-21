package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.request.CreateJobRequest;
import com.netmap.netmapservice.domain.request.FilterJobRequest;
import com.netmap.netmapservice.domain.response.JobPostResponse;
import com.netmap.netmapservice.domain.response.JobSummaryResponse;
import com.netmap.netmapservice.mapper.JobMapper;
import com.netmap.netmapservice.model.*;
import com.netmap.netmapservice.repository.*;
import com.netmap.netmapservice.service.JobService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netmap.netmapservice.model.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final AppUserRepository appUserRepository;
    private final EmployerRepository employerRepository;
    private final JobPostingRepository jobPostingRepository;
    private final SkillRepository skillRepository;
    private final JobMapper jobMapper;

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
    @Transactional
    public void deleteJob(UUID jobId, UUID userId) {
        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Optional<AppUser> userOpt = appUserRepository.findById(userId);

        if (userOpt.isEmpty()) {
            jobPostingRepository.delete(job);
            return;
        }

        AppUser user = userOpt.get();

        if (user.getRole() == Role.ADMIN ||
                (user.getRole() == Role.EMPLOYER && job.getEmployer().getAppUser().getId().equals(userId))) {
            jobPostingRepository.delete(job);
        } else {
            throw new RuntimeException("Unauthorized to delete this job");
        }
    }



    @Override
    public List<JobPostResponse> getJobsForUser(UUID userId) {
        Optional<AppUser> userOpt = appUserRepository.findById(userId);


        if (userOpt.isEmpty()) {
            return jobPostingRepository.findAll()
                    .stream()
                    .map(JobPostResponse::new)
                    .collect(Collectors.toList());
        }

        AppUser user = userOpt.get();

        List<JobPosting> jobs = switch (user.getRole()) {
            case ADMIN -> jobPostingRepository.findAll();
            case EMPLOYER -> jobPostingRepository.findByEmployerAppUserId(userId);
            default -> jobPostingRepository.findByVerifiedTrue();
        };

        return jobs.stream()
                .map(JobPostResponse::new)
                .collect(Collectors.toList());
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
        List<JobPosting> jobs;

        boolean hasSkillFilter = request.getSkills() != null && !request.getSkills().isEmpty();

        if (hasSkillFilter) {
            jobs = jobPostingRepository.filterWithSkills(
                    request.getSkills(),
                    request.getIsRemote(),
                    request.getIsFreelance()
            );
        } else {
            jobs = jobPostingRepository.filterWithoutSkills(
                    request.getIsRemote(),
                    request.getIsFreelance()
            );
        }

        return jobs.stream()
                .filter(job -> {
                    if (request.getRadiusKm() == null ||
                            request.getUserLatitude() == null ||
                            request.getUserLongitude() == null) {
                        return true;
                    }

                    double userLat = request.getUserLatitude().doubleValue();
                    double userLon = request.getUserLongitude().doubleValue();
                    double jobLat = job.getLatitude().doubleValue();
                    double jobLon = job.getLongitude().doubleValue();

                    return haversine(userLat, userLon, jobLat, jobLon) <= request.getRadiusKm();
                })
                .map(jobMapper::toSummary)
                .collect(Collectors.toList());
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
