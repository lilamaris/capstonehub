package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.AcademicUnit;
import com.icnet.capstonehub.domain.model.Department;
import com.icnet.capstonehub.domain.model.Faculty;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.UUID;

public class AcademicUnitResult {
    @Builder
    public record Query(
            UUID id,
            EditionResult edition,
            TimelineResult timeline,
            FacultyResult faculty,
            DepartmentResult department
    ) {
        public static Query from(AcademicUnit domain, Faculty faculty, Department department) {
            return Query.builder()
                    .id(domain.id().value())
                    .faculty(FacultyResult.from(faculty))
                    .department(DepartmentResult.from(department))
                    .edition(EditionResult.from(domain.edition()))
                    .timeline(TimelineResult.from(domain.timeline()))
                    .build();
        }
    }

    @Builder
    public record Command(
            UUID id,
            EditionResult edition,
            TimelineResult timeline,
            UUID facultyId,
            UUID departmentId
    ) {
        public static Command from(AcademicUnit domain) {
            return Command.builder()
                    .id(domain.id().value())
                    .facultyId(domain.facultyId().value())
                    .departmentId(domain.departmentId().value())
                    .edition(EditionResult.from(domain.edition()))
                    .timeline(TimelineResult.from(domain.timeline()))
                    .build();
        }
    }
}
