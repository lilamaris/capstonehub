package com.icnet.capstonehub.domain;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record Version (
    Id id,
    LineageId lineageId,
    LineageScope lineageScope,
    LocalDate validFrom,
    LocalDate validTo,
    Integer versionNo,
    String versionDescription
) {
    public record Id(UUID value) {}
    public record LineageId(UUID value) {}
    public enum LineageScope { AFFILIATION, COURSE }

    public Version next(LocalDate validFrom, LocalDate validTo, String versionDescription) {
        return Version.builder()
                .lineageId(lineageId)
                .lineageScope(lineageScope)
                .versionNo(versionNo + 1)
                .validFrom(validFrom)
                .validTo(validTo)
                .versionDescription(versionDescription)
                .build();
    }
}
