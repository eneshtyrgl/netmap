package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.domain.request.CreateJobRequest;
import com.netmap.netmapservice.domain.response.JobPostResponse;
import com.netmap.netmapservice.security.CustomPrincipal;
import com.netmap.netmapservice.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<JobPostResponse> createJob(@RequestBody @Valid CreateJobRequest request) {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID employerId = principal.getUserId();

        JobPostResponse response = jobService.createJob(employerId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<JobPostResponse>> listJobs() {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = principal.getUserId();
        return ResponseEntity.ok(jobService.getJobsForUser(userId));
    }
}
