package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Timeline;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TimelineResult(
        UUID id,
        UUID sharedId,
        String scope,
        LocalDateTime validFrom,
        LocalDateTime validTo
) {
    public static TimelineResult from(Timeline domain) {
        return TimelineResult.builder()
                .id(domain.id().value())
                .sharedId(domain.sharedId().value())
                .validFrom(domain.validPeriod().from())
                .validTo(domain.validPeriod().to())
                .build();
    }
}
