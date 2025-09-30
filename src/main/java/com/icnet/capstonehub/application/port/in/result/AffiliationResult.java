package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Affiliation;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AffiliationResult(
        UUID id,
        VersionResult version,
        LineageResult lineage,
        CollegeResult college,
        MajorResult major
) {
    public static AffiliationResult from(Affiliation domain) {
        return AffiliationResult.builder()
                .id(domain.id().value())
                .college(CollegeResult.from(domain.college()))
                .major(MajorResult.from(domain.major()))
                .version(VersionResult.from(domain.version()))
                .lineage(LineageResult.from(domain.lineage()))
                .build();
    }

}
