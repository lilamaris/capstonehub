package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Faculty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FacultyResult(
    UUID id,
    String name
) {
    public static FacultyResult from(Faculty domain) {
        return FacultyResult.builder()
                .id(domain.id().value())
                .name(domain.name())
                .build();
    }
}
