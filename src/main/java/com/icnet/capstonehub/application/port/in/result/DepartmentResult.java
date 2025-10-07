package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Department;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DepartmentResult(
    UUID id,
    String name
) {
    public static DepartmentResult from(Department domain) {
        return DepartmentResult.builder()
                .id(domain.id().value())
                .name(domain.name())
                .build();
    }
}
