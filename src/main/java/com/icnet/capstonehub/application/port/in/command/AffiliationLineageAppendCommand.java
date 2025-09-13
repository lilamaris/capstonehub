package com.icnet.capstonehub.application.port.in.command;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AffiliationLineageAppendCommand(
        UUID lineageSharedId,
        UUID collegeId,
        UUID majorId,
        LocalDateTime validAt,
        String versionDescription
) {}
