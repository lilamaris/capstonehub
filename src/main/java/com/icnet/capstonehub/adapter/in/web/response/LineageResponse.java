package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.LineageResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record LineageResponse(
    UUID id,
    UUID sharedId,
    String scope,
    LocalDateTime validFrom,
    LocalDateTime validTo
) {
    public static LineageResponse from(LineageResult result) {
        return LineageResponse.builder()
                .id(result.id())
                .sharedId(result.sharedId())
                .scope(result.scope())
                .validFrom(result.validFrom())
                .validTo(result.validTo())
                .build();
    }
}
