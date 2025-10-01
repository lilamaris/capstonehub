package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.CollegeResult;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CollegeResponse(UUID id, String name) {
    public static CollegeResponse from(CollegeResult result) {
        return CollegeResponse.builder()
                .id(result.id())
                .name(result.name())
                .build();
    }
}
