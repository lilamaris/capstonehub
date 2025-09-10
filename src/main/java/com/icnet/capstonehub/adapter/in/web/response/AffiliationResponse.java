package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AffiliationResponse(
        UUID id,
        VersionResponse version,
        LineageResponse lineage,
        CollegeResponse college,
        MajorResponse major
) {}
