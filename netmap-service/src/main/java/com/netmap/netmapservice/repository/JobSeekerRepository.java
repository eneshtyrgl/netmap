package com.netmap.netmapservice.repository;

import com.netmap.netmapservice.model.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, UUID> {
    Optional<JobSeeker> findByAppUserId(UUID appUserId);
}
