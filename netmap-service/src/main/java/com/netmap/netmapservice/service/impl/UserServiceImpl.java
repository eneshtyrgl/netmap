package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.request.UpdateProfileRequest;
import com.netmap.netmapservice.domain.response.ProfileResponse;
import com.netmap.netmapservice.model.*;
import com.netmap.netmapservice.repository.*;
import com.netmap.netmapservice.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final SkillRepository skillRepository;
    private final EmployerRepository employerRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository,
                           JobSeekerRepository jobSeekerRepository,
                           SkillRepository skillRepository,
                           EmployerRepository employerRepository,
                           CompanyRepository companyRepository) {
        this.appUserRepository = appUserRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.skillRepository = skillRepository;
        this.employerRepository = employerRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public ProfileResponse getProfile(UUID userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UUID> skillIds = List.of();
        String educationLevel = null;
        Integer experienceYears = null;
        UUID companyId = null;

        if (user.getRole().name().equals("JOB_SEEKER")) {
            JobSeeker jobSeeker = jobSeekerRepository.findByAppUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Job seeker not found"));

            skillIds = jobSeeker.getSkills().stream()
                    .map(Skill::getId)
                    .toList();

            educationLevel = jobSeeker.getEducationLevel() != null
                    ? jobSeeker.getEducationLevel().name()
                    : null;

            experienceYears = jobSeeker.getExperienceYears();
        } else if (user.getRole().name().equals("EMPLOYER")) {
            Employer employer = employerRepository.findByAppUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Employer not found"));

            if (employer.getCompany() != null) {
                companyId = employer.getCompany().getId();
            }
        }

        return new ProfileResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRole().name(),
                skillIds,
                educationLevel,
                experienceYears,
                companyId
        );
    }


    @Override
    @Transactional
    public void updateProfile(UUID userId, UpdateProfileRequest request) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        appUserRepository.save(user);

        if (user.getRole().name().equals("JOB_SEEKER")) {
            JobSeeker jobSeeker = jobSeekerRepository.findByAppUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Job seeker not found"));

            if (request.getExperienceYears() != null) {
                jobSeeker.setExperienceYears(request.getExperienceYears());
            }

            if (request.getEducationLevel() != null) {
                jobSeeker.setEducationLevel(EducationLevel.valueOf(request.getEducationLevel()));
            }

            if (request.getSkills() != null) {
                List<UUID> skillIds = request.getSkills().stream()
                        .map(UUID::fromString)
                        .toList();

                List<Skill> skills = skillRepository.findAllById(skillIds);
                jobSeeker.setSkills(skills);
            }

            jobSeekerRepository.save(jobSeeker);
        } else if (user.getRole().name().equals("EMPLOYER")) {
            Employer employer = employerRepository.findByAppUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Employer not found"));

            if (request.getCompanyId() != null) {
                Company company = companyRepository.findById(request.getCompanyId())
                        .orElseThrow(() -> new RuntimeException("Company not found"));
                employer.setCompany(company);
            }

            employerRepository.save(employer);
        }
    }
}
