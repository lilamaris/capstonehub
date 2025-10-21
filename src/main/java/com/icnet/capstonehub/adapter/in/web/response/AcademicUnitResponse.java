package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.AcademicUnitResult;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AcademicUnitResponse(
        UUID id,
        EditionResponse edition,
        TimelineResponse timeline,
        UUID facultyId,
        UUID departmentId
) {
    public static AcademicUnitResponse from(AcademicUnitResult result) {
        return AcademicUnitResponse.builder()
                .id(result.id())
                .edition(EditionResponse.from(result.edition()))
                .timeline(TimelineResponse.from(result.timeline()))
                .facultyId(result.facultyId())
                .departmentId(result.departmentId())
                .build();
    }
}
