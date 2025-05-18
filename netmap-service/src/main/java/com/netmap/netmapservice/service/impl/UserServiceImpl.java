package com.netmap.netmapservice.service.impl;

import com.netmap.netmapservice.domain.request.UpdateProfileRequest;
import com.netmap.netmapservice.domain.response.ProfileResponse;
import com.netmap.netmapservice.model.AppUser;
import com.netmap.netmapservice.model.EducationLevel;
import com.netmap.netmapservice.model.JobSeeker;
import com.netmap.netmapservice.model.Skill;
import com.netmap.netmapservice.repository.AppUserRepository;
import com.netmap.netmapservice.repository.JobSeekerRepository;
import com.netmap.netmapservice.repository.SkillRepository;
import com.netmap.netmapservice.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository,
                           JobSeekerRepository jobSeekerRepository,
                           SkillRepository skillRepository) {
        this.appUserRepository = appUserRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public ProfileResponse getProfile(UUID userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> skillNames = List.of();

        if (user.getRole().name().equals("JOB_SEEKER")) {
            JobSeeker jobSeeker = jobSeekerRepository.findByAppUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Job seeker not found"));

            skillNames = jobSeeker.getSkills().stream()
                    .map(Skill::getName)
                    .collect(Collectors.toList());
        }

        return new ProfileResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRole().name(),
                skillNames
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
                List<Skill> skills = skillRepository.findByNameIn(request.getSkills());
                jobSeeker.setSkills(skills);
            }

            jobSeekerRepository.save(jobSeeker);
        }
    }
}
