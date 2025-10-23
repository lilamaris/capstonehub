package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.AcademicUnitQueryUseCase;
import com.icnet.capstonehub.application.port.in.result.AcademicUnitResult;
import com.icnet.capstonehub.application.port.out.AcademicUnitPort;
import com.icnet.capstonehub.application.port.out.DepartmentPort;
import com.icnet.capstonehub.application.port.out.FacultyPort;
import com.icnet.capstonehub.domain.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AcademicUnitQueryService implements AcademicUnitQueryUseCase {
    private final AcademicUnitPort academicUnitPort;
    private final FacultyPort facultyPort;
    private final DepartmentPort departmentPort;

    @Override
    public List<AcademicUnitResult.Query> getOwned(User.Id id) {
        var units = academicUnitPort.getAllByUserId(id);
        var facultyIds = units.stream().map(AcademicUnit::facultyId).toList();
        var departmentIds = units.stream().map(AcademicUnit::departmentId).toList();

        var faculties = facultyPort.getAll(facultyIds).stream().collect(Collectors.toMap(Faculty::id, Function.identity()));
        var departments = departmentPort.getAll(departmentIds).stream().collect(Collectors.toMap(Department::id, Function.identity()));

        return units.stream()
                .map(academicUnit -> AcademicUnitResult.Query.from(
                        academicUnit,
                        faculties.get(academicUnit.facultyId()),
                        departments.get(academicUnit.departmentId())
                ))
                .toList();
    }

    @Override
    public List<AcademicUnitResult.Query> getTimeline(Timeline.SharedId id) {
        var units = academicUnitPort.getTimeline(id);
        var facultyIds = units.stream().map(AcademicUnit::facultyId).toList();
        var departmentIds = units.stream().map(AcademicUnit::departmentId).toList();

        var faculties = facultyPort.getAll(facultyIds).stream().collect(Collectors.toMap(Faculty::id, Function.identity()));
        var departments = departmentPort.getAll(departmentIds).stream().collect(Collectors.toMap(Department::id, Function.identity()));

        return units.stream()
                .map(academicUnit -> AcademicUnitResult.Query.from(
                        academicUnit,
                        faculties.get(academicUnit.facultyId()),
                        departments.get(academicUnit.departmentId())
                ))
                .toList();
    }

    @Override
    public List<AcademicUnitResult.Query> getSnapshot(Timeline.SharedId id, LocalDateTime txAt) {
        var units = academicUnitPort.getSnapshot(id, txAt);
        var facultyIds = units.stream().map(AcademicUnit::facultyId).toList();
        var departmentIds = units.stream().map(AcademicUnit::departmentId).toList();

        var faculties = facultyPort.getAll(facultyIds).stream().collect(Collectors.toMap(Faculty::id, Function.identity()));
        var departments = departmentPort.getAll(departmentIds).stream().collect(Collectors.toMap(Department::id, Function.identity()));

        return units.stream()
                .map(academicUnit -> AcademicUnitResult.Query.from(
                        academicUnit,
                        faculties.get(academicUnit.facultyId()),
                        departments.get(academicUnit.departmentId())
                ))
                .toList();
    }
}
