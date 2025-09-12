package com.icnet.capstonehub.application.port.in.result;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record LineageResult(
        UUID id,
        UUID sharedId,
        String scope,
        LocalDateTime validFrom,
        LocalDateTime validTo
) {}
