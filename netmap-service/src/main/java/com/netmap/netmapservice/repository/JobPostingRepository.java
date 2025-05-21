package com.netmap.netmapservice.repository;

import com.netmap.netmapservice.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobPostingRepository extends JpaRepository<JobPosting, UUID> {

    List<JobPosting> findByVerifiedTrue();

    @Query("SELECT j FROM JobPosting j WHERE j.employer.appUser.id = :userId")
    List<JobPosting> findByEmployerAppUserId(UUID userId);

    List<JobPosting> findByVerifiedFalse();

    @Query("""
      SELECT DISTINCT jp FROM JobPosting jp
      JOIN jp.skills s
      WHERE jp.verified = true
        AND (:skillIds IS NULL OR s.id IN :skillIds)
        AND (:isRemote IS NULL OR jp.isRemote = :isRemote)
        AND (:isFreelance IS NULL OR jp.isFreelance = :isFreelance)
    """)
    List<JobPosting> filterWithSkills(
            @Param("skillIds") List<UUID> skillIds,
            @Param("isRemote") Boolean isRemote,
            @Param("isFreelance") Boolean isFreelance
    );

    @Query("""
      SELECT jp FROM JobPosting jp
      WHERE jp.verified = true
        AND (:isRemote IS NULL OR jp.isRemote = :isRemote)
        AND (:isFreelance IS NULL OR jp.isFreelance = :isFreelance)
    """)
    List<JobPosting> filterWithoutSkills(
            @Param("isRemote") Boolean isRemote,
            @Param("isFreelance") Boolean isFreelance
    );
}
