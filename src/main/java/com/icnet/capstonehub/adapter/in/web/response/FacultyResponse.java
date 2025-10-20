package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.FacultyResult;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FacultyResponse(UUID id, String name, AuditResponse audit) {
    public static FacultyResponse from(FacultyResult result) {
        return FacultyResponse.builder()
                .id(result.id())
                .name(result.name())
                .audit(AuditResponse.from(result.audit()))
                .build();
    }
}
