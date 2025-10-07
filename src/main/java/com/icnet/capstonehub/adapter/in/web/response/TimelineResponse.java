package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.TimelineResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TimelineResponse(
    UUID id,
    UUID sharedId,
    String scope,
    LocalDateTime validFrom,
    LocalDateTime validTo
) {
    public static TimelineResponse from(TimelineResult result) {
        return TimelineResponse.builder()
                .id(result.id())
                .sharedId(result.sharedId())
                .scope(result.scope())
                .validFrom(result.validFrom())
                .validTo(result.validTo())
                .build();
    }
}
