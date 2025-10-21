package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.AcademicUnit;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AcademicUnitResult(
        UUID id,
        EditionResult edition,
        TimelineResult timeline,
        UUID facultyId,
        UUID departmentId
) {
    public static AcademicUnitResult from(AcademicUnit domain) {
        return AcademicUnitResult.builder()
                .id(domain.id().value())
                .facultyId(domain.facultyId().value())
                .departmentId(domain.departmentId().value())
                .edition(EditionResult.from(domain.edition()))
                .timeline(TimelineResult.from(domain.timeline()))
                .build();
    }

}
