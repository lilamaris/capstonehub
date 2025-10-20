package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.DepartmentResult;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DepartmentResponse(UUID id, String name, AuditResponse audit) {
    public static DepartmentResponse from(DepartmentResult result) {
        return DepartmentResponse.builder()
                .id(result.id())
                .name(result.name())
                .audit(AuditResponse.from(result.audit()))
                .build();
    }
}
