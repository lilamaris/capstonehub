package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Major;
import lombok.Builder;

import java.util.UUID;

@Builder
public record MajorResult(
    UUID id,
    String name
) {
    public static MajorResult from(Major domain) {
        return MajorResult.builder()
                .id(domain.id().value())
                .name(domain.name())
                .build();
    }
}
