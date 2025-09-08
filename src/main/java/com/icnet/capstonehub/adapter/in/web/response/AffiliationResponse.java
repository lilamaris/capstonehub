package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AffiliationResponse(
        UUID id,
        CollegeResponse college,
        MajorResponse major,
        VersionResponse version
) {}
