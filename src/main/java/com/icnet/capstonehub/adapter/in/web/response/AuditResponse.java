package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.AuditResult;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AuditResponse(
        String createdBy,
        LocalDateTime createdAt,
        String updatedBy,
        LocalDateTime updatedAt
) {
    public static AuditResponse from(AuditResult result) {
        return AuditResponse.builder()
                .createdBy(result.createdBy().toString())
                .createdAt(result.createdAt())
                .updatedBy(result.updatedBy().toString())
                .updatedAt(result.updatedAt())
                .build();
    }
}
