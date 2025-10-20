package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Audit;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AuditResult(
        UUID createdBy,
        LocalDateTime createdAt,
        UUID updatedBy,
        LocalDateTime updatedAt
) {
    public static AuditResult from(Audit domain) {
        return AuditResult.builder()
                .createdBy(domain.createdBy().value())
                .createdAt(domain.createdAt())
                .updatedBy(domain.updatedBy().value())
                .updatedAt(domain.updatedAt())
                .build();
    }
}
