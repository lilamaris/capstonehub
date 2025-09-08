package com.icnet.capstonehub.application.port.in.result;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AffiliationResult(
        UUID id,
        UUID lineageId,
        CollegeResult college,
        MajorResult major,
        VersionResult version
) {}
