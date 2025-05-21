package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.domain.request.CreateJobRequest;
import com.netmap.netmapservice.domain.request.FilterJobRequest;
import com.netmap.netmapservice.domain.response.JobPostResponse;
import com.netmap.netmapservice.domain.response.JobSummaryResponse;
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
    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJob(@PathVariable UUID jobId,
                                          @RequestHeader("X-User-Id") UUID userId) {
        jobService.deleteJob(jobId, userId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<JobPostResponse>> listJobs() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomPrincipal principal) {
            UUID userId = principal.getUserId();
            return ResponseEntity.ok(jobService.getJobsForUser(userId));
        } else {
            return ResponseEntity.ok(jobService.getJobsForUser(null));
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<List<JobSummaryResponse>> filterJobs(@RequestBody FilterJobRequest request) {
        return ResponseEntity.ok(jobService.filterJobs(request));
    }


}
