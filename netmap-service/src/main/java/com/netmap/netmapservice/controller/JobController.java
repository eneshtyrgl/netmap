package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.domain.request.CreateJobRequest;
import com.netmap.netmapservice.domain.response.JobPostResponse;
import com.netmap.netmapservice.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobPostResponse> createJob(@RequestParam UUID employerId,
                                                     @RequestBody CreateJobRequest request) {
        return ResponseEntity.ok(jobService.createJob(employerId, request));
    }

    @GetMapping
    public ResponseEntity<List<JobPostResponse>> listJobs() {
        return ResponseEntity.ok(jobService.listAllJobs());
    }

}
