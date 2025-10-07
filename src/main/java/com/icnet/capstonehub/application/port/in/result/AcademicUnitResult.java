package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.AcademicUnit;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AcademicUnitResult(
        UUID id,
        EditionResult edition,
        TimelineResult timeline,
        FacultyResult faculty,
        DepartmentResult department
) {
    public static AcademicUnitResult from(AcademicUnit domain) {
        return AcademicUnitResult.builder()
                .id(domain.id().value())
                .faculty(FacultyResult.from(domain.faculty()))
                .department(DepartmentResult.from(domain.department()))
                .edition(EditionResult.from(domain.edition()))
                .timeline(TimelineResult.from(domain.timeline()))
                .build();
    }

}
