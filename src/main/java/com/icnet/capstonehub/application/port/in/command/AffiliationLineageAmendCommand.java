package com.icnet.capstonehub.application.port.in.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AffiliationLineageAmendCommand(
        UUID lineageSharedId,
        UUID versionSharedId,
        UUID collegeId,
        UUID majorId,
        String versionDescription
) {}
