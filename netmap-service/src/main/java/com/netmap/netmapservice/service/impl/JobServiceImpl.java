package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.request.CreateJobRequest;
import com.netmap.netmapservice.domain.response.JobPostResponse;
import com.netmap.netmapservice.model.Employer;
import com.netmap.netmapservice.model.JobPosting;
import com.netmap.netmapservice.model.Skill;
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

    private final EmployerRepository employerRepository;
    private final JobPostingRepository jobPostingRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public JobServiceImpl(EmployerRepository employerRepository,
                          JobPostingRepository jobPostingRepository,
                          SkillRepository skillRepository) {
        this.employerRepository = employerRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional
    public JobPostResponse createJob(UUID employerId, CreateJobRequest request) {
        Employer employer = employerRepository.findById(employerId)
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
                .skills(requiredSkills)
                .build();

        jobPostingRepository.save(jobPosting);

        return new JobPostResponse(jobPosting);
    }

    @Override
    public List<JobPostResponse> listAllJobs() {
        return jobPostingRepository.findAll().stream()
                .map(JobPostResponse::new)
                .collect(Collectors.toList());
    }
}
