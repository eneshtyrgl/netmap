package com.netmap.netmapservice.repository;

import com.netmap.netmapservice.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JobPostingRepository extends JpaRepository<JobPosting, UUID> {

    List<JobPosting> findByVerifiedTrue();

    @Query("SELECT j FROM JobPosting j WHERE j.employer.appUser.id = :userId")
    List<JobPosting> findByEmployerAppUserId(UUID userId);

    List<JobPosting> findByVerifiedFalse();

}
