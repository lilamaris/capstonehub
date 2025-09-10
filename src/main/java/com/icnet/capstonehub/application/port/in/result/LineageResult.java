package com.icnet.capstonehub.application.port.in.result;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record LineageResult(
        UUID id,
        UUID lineageId,
        String lineageScope,
        LocalDate validFrom,
        LocalDate validTo
) {}
