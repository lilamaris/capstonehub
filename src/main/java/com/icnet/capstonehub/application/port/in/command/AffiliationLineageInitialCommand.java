package com.icnet.capstonehub.application.port.in.command;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record AffiliationLineageInitialCommand(
        UUID collegeId,
        UUID majorId,
        LocalDate validFrom,
        LocalDate validTo,
        String versionDescription
) {}
