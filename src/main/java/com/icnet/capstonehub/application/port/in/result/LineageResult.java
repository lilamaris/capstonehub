package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Lineage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record LineageResult(
        UUID id,
        UUID sharedId,
        String scope,
        LocalDateTime validFrom,
        LocalDateTime validTo
) {
    public static LineageResult from(Lineage domain) {
        return LineageResult.builder()
                .id(domain.id().value())
                .sharedId(domain.sharedId().value())
                .scope(domain.scope().name())
                .validFrom(domain.validPeriod().from())
                .validTo(domain.validPeriod().to())
                .build();
    }
}
