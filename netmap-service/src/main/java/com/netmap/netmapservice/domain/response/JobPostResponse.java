package com.netmap.netmapservice.domain.response;

import com.netmap.netmapservice.model.JobPosting;
import com.netmap.netmapservice.model.Skill;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class JobPostResponse {
    private UUID id;
    private String title;
    private String description;
    private Integer salary;
    private Boolean isRemote;
    private Boolean isFreelance;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDate postDate;
    private List<String> skills;
    private Boolean verified;
    private String companyName;

    public JobPostResponse(JobPosting job) {
        this.id = job.getId();
        this.title = job.getTitle();
        this.description = job.getDescription();
        this.salary = job.getSalary();
        this.isRemote = job.getIsRemote();
        this.isFreelance = job.getIsFreelance();
        this.latitude = job.getLatitude();
        this.longitude = job.getLongitude();
        this.postDate = job.getPostDate();
        this.skills = job.getSkills().stream()
                .map(Skill::getName)
                .collect(Collectors.toList());
        this.verified = job.getVerified();
        this.companyName = job.getEmployer().getCompany() != null
                ? job.getEmployer().getCompany().getName()
                : "Unknown Company";

    }
}
