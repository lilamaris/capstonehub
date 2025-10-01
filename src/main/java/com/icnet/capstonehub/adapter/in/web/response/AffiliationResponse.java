package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.AffiliationResult;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AffiliationResponse(
        UUID id,
        VersionResponse version,
        LineageResponse lineage,
        CollegeResponse college,
        MajorResponse major
) {
    public static AffiliationResponse from(AffiliationResult result) {
        return AffiliationResponse.builder()
                .id(result.id())
                .version(VersionResponse.from(result.version()))
                .lineage(LineageResponse.from(result.lineage()))
                .college(CollegeResponse.from(result.college()))
                .major(MajorResponse.from(result.major()))
                .build();
    }
}
