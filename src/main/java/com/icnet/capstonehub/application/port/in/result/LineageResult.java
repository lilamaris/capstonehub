package com.icnet.capstonehub.application.port.in.result;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record LineageResult(
        UUID id,
        UUID sharedId,
        String scope,
        LocalDate validFrom,
        LocalDate validTo
) {}
