package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.AcademicUnitResult;
import lombok.Builder;

import java.util.UUID;

public class AcademicUnitResponse {
    @Builder
    public record Command (
        UUID id,
        EditionResponse edition,
        TimelineResponse timeline,
        UUID facultyId,
        UUID departmentId
    ) {
        public static Command from(AcademicUnitResult.Command result) {
            return Command.builder()
                    .id(result.id())
                    .edition(EditionResponse.from(result.edition()))
                    .timeline(TimelineResponse.from(result.timeline()))
                    .facultyId(result.facultyId())
                    .departmentId(result.departmentId())
                    .build();
        }
    }

    @Builder
    public record Query (
            UUID id,
            EditionResponse edition,
            TimelineResponse timeline,
            FacultyResponse faculty,
            DepartmentResponse department
    ) {
        public static Query from(AcademicUnitResult.Query result) {
            return Query.builder()
                    .id(result.id())
                    .edition(EditionResponse.from(result.edition()))
                    .timeline(TimelineResponse.from(result.timeline()))
                    .faculty(FacultyResponse.from(result.faculty()))
                    .department(DepartmentResponse.from(result.department()))
                    .build();
        }
    }
}
