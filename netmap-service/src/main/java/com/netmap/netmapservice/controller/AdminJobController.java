package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.domain.response.JobPostResponse;
import com.netmap.netmapservice.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/jobs")
@RequiredArgsConstructor
public class AdminJobController {

    private final JobService jobService;

    @GetMapping("/unverified")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<JobPostResponse>> getUnverifiedJobs() {
        return ResponseEntity.ok(jobService.getUnverifiedJobs());
    }

    @PutMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> verifyJob(@PathVariable UUID id) {
        jobService.verifyJobPosting(id);
        return ResponseEntity.noContent().build();
    }
}
