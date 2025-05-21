package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.domain.request.ApplyJobRequest;
import com.netmap.netmapservice.domain.response.ApplicationResponse;
import com.netmap.netmapservice.domain.response.ApplicationSummaryResponse;
import com.netmap.netmapservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping
    public ResponseEntity<Void> apply(@RequestBody ApplyJobRequest request) {
        boolean success = applicationService.applyToJob(request.getUserId(), request.getJobPostingId());
        if (!success) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{appUserId}")
    public ResponseEntity<List<ApplicationSummaryResponse>> getUserApplications(@PathVariable UUID appUserId) {
        return ResponseEntity.ok(applicationService.getApplicationSummariesByUser(appUserId));
    }

    @GetMapping("/job/{jobPostingId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByJob(@PathVariable UUID jobPostingId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJobPosting(jobPostingId));
    }
}
