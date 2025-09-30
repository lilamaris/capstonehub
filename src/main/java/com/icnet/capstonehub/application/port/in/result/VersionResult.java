package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Version;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record VersionResult(
        UUID id,
        UUID sharedId,
        Integer versionNo,
        String versionDescription,
        LocalDateTime txFrom,
        LocalDateTime txTo
) {
    public static VersionResult from(Version domain) {
        return VersionResult.builder()
                .id(domain.id().value())
                .sharedId(domain.sharedId().value())
                .txFrom(domain.txPeriod().from())
                .txTo(domain.txPeriod().to())
                .versionNo(domain.versionNo())
                .versionDescription(domain.versionDescription())
                .build();
    }
}
