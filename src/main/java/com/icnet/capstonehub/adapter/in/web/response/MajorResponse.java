package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.MajorResult;
import lombok.Builder;

import java.util.UUID;

@Builder
public record MajorResponse(UUID id, String name) {
    public static MajorResponse from(MajorResult result) {
        return MajorResponse.builder()
                .id(result.id())
                .name(result.name())
                .build();
    }
}
