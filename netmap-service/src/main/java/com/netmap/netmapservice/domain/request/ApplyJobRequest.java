package com.netmap.netmapservice.domain.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ApplyJobRequest {
    private UUID jobId;
}
