package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Edition;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record EditionResult(
        UUID id,
        UUID sharedId,
        Integer editionNo,
        String editionDescription,
        LocalDateTime txFrom,
        LocalDateTime txTo
) {
    public static EditionResult from(Edition domain) {
        return EditionResult.builder()
                .id(domain.id().value())
                .sharedId(domain.sharedId().value())
                .txFrom(domain.txPeriod().from())
                .txTo(domain.txPeriod().to())
                .editionNo(domain.editionNo())
                .editionDescription(domain.editionDescription())
                .build();
    }
}
