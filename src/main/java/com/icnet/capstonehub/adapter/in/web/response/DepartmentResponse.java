package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.DepartmentResult;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DepartmentResponse(UUID id, String name) {
    public static DepartmentResponse from(DepartmentResult result) {
        return DepartmentResponse.builder()
                .id(result.id())
                .name(result.name())
                .build();
    }
}
