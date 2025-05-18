package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.domain.response.ApplicationResponse;
import com.netmap.netmapservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Void> apply(@RequestParam UUID jobSeekerId,
                                      @RequestParam UUID jobPostingId) {
        applicationService.applyToJob(jobSeekerId, jobPostingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{appUserId}")
    public ResponseEntity<List<ApplicationResponse>> getUserApplications(@PathVariable UUID appUserId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJobSeeker(appUserId));
    }

    @GetMapping("/job/{jobPostingId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByJob(@PathVariable UUID jobPostingId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJobPosting(jobPostingId));
    }
}
