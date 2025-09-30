package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.College;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CollegeResult (
    UUID id,
    String name
) {
    public static CollegeResult from(College domain) {
        return CollegeResult.builder()
                .id(domain.id().value())
                .name(domain.name())
                .build();
    }
}
