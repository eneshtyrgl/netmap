package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.domain.response.ApplicationResponse;
import com.netmap.netmapservice.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<Void> applyToJob(
            @RequestHeader("User-Id") UUID jobSeekerId,
            @RequestParam UUID jobId) {
        try {
            applicationService.applyToJob(jobSeekerId, jobId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByJobSeeker(
            @PathVariable("id") UUID jobSeekerId) {
        try {
            List<ApplicationResponse> applications = applicationService.getApplicationsByJobSeeker(jobSeekerId);
            return ResponseEntity.ok(applications);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/employer/{id}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByEmployer(
            @PathVariable("id") UUID employerId) {
        try {
            List<ApplicationResponse> applications = applicationService.getApplicationsByJobPosting(employerId);
            return ResponseEntity.ok(applications);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 