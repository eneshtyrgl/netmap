package com.netmap.netmapservice.repository;

import com.netmap.netmapservice.model.JobApplication;
import com.netmap.netmapservice.model.JobPosting;
import com.netmap.netmapservice.model.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
    boolean existsByJobSeekerAndJobPosting(JobSeeker jobSeeker, JobPosting jobPosting);
    List<JobApplication> findByJobSeeker(JobSeeker jobSeeker);
    List<JobApplication> findByJobPosting(JobPosting jobPosting);
}