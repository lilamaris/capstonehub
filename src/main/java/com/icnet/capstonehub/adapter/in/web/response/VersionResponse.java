package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.VersionResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record VersionResponse(
    UUID id,
    UUID sharedId,
    Integer versionNo,
    String versionDescription,
    LocalDateTime txFrom,
    LocalDateTime txTo
) {
    public static VersionResponse from(VersionResult result) {
        return VersionResponse.builder()
                .id(result.id())
                .sharedId(result.sharedId())
                .versionNo(result.versionNo())
                .versionDescription(result.versionDescription())
                .txFrom(result.txFrom())
                .txTo(result.txTo())
                .build();
    }
}
