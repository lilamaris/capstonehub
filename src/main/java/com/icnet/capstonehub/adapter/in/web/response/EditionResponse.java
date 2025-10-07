package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.EditionResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record EditionResponse(
    UUID id,
    UUID sharedId,
    Integer editionNo,
    String editionDescription,
    LocalDateTime txFrom,
    LocalDateTime txTo
) {
    public static EditionResponse from(EditionResult result) {
        return EditionResponse.builder()
                .id(result.id())
                .sharedId(result.sharedId())
                .editionNo(result.editionNo())
                .editionDescription(result.editionDescription())
                .txFrom(result.txFrom())
                .txTo(result.txTo())
                .build();
    }
}
