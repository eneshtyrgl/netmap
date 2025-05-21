package com.netmap.netmapservice.domain.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Data
public class ApplyJobRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID jobPostingId;
}
