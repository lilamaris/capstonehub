package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.AcademicUnitResult;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AcademicUnitResponse(
        UUID id,
        EditionResponse edition,
        TimelineResponse timeline,
        FacultyResponse faculty,
        DepartmentResponse department
) {
    public static AcademicUnitResponse from(AcademicUnitResult result) {
        return AcademicUnitResponse.builder()
                .id(result.id())
                .edition(EditionResponse.from(result.edition()))
                .timeline(TimelineResponse.from(result.timeline()))
                .faculty(FacultyResponse.from(result.faculty()))
                .department(DepartmentResponse.from(result.department()))
                .build();
    }
}
