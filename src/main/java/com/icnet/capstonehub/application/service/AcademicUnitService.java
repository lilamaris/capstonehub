package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.AcademicUnitUseCase;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAmendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAppendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitInitialTimelineCommand;
import com.icnet.capstonehub.application.port.in.result.AcademicUnitResult;
import com.icnet.capstonehub.application.port.out.AcademicUnitPort;
import com.icnet.capstonehub.application.port.out.FacultyPort;
import com.icnet.capstonehub.application.port.out.DepartmentPort;
import com.icnet.capstonehub.domain.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AcademicUnitService implements AcademicUnitUseCase {
    private final AcademicUnitPort academicUnitPort;

    private final FacultyPort facultyPort;
    private final DepartmentPort departmentPort;

    @Override
    public List<AcademicUnitResult> getAcademicUnitTimeline(UUID timelineSharedId) {
        var now = LocalDateTime.now();
        return academicUnitPort.getTimelineOfSnapshot(new Timeline.SharedId(timelineSharedId), now).stream()
                .map(AcademicUnitResult::from)
                .toList();
    }

    @Override
    public List<AcademicUnitResult> getAcademicUnitTimeline(UUID timelineSharedId, LocalDateTime txAt) {
        return academicUnitPort.getTimelineOfSnapshot(new Timeline.SharedId(timelineSharedId), txAt).stream()
                .map(AcademicUnitResult::from)
                .toList();
    }

    @Override
    public AcademicUnitResult initialAcademicUnitTimeline(AcademicUnitInitialTimelineCommand command) {
        var now = LocalDateTime.now();
        var facultyId = new Faculty.Id(command.facultyId());
        var departmentId = new Department.Id(command.departmentId());

        var initial = AcademicUnit.initial(facultyId, departmentId, now, command.validAt());

        return AcademicUnitResult.from(academicUnitPort.save(initial));
    }

    @Override
    public AcademicUnitResult appendAcademicUnitTimeline(AcademicUnitAppendTimelineCommand command) {
        var now = LocalDateTime.now();
        var timelineSharedId = new Timeline.SharedId(command.timelineSharedId());
        var facultyId = new Faculty.Id(command.facultyId());
        var departmentId = new Department.Id(command.departmentId());

        var headAcademicUnit = academicUnitPort.getSnapshotOfRecord(timelineSharedId, command.validAt(), now)
                .orElseThrow(EntityNotFoundException::new);
        var appendTransition = headAcademicUnit.append(facultyId, departmentId, now, command.validAt());
        academicUnitPort.save(appendTransition.previous());

        return AcademicUnitResult.from(academicUnitPort.save(appendTransition.next()));
    }

    @Override
    public AcademicUnitResult amendAcademicUnitTimeline(AcademicUnitAmendTimelineCommand command) {
        var now = LocalDateTime.now();
        var timelineSharedId = new Timeline.SharedId(command.timelineSharedId());
        var editionSharedId = new Edition.SharedId(command.editionSharedId());
        var facultyId = new Faculty.Id(command.facultyId());
        var departmentId = new Department.Id(command.departmentId());

        var headAcademicUnit = academicUnitPort.getSnapshotOfRecord(timelineSharedId, editionSharedId, now)
                .orElseThrow(EntityNotFoundException::new);
        var amendTransition = headAcademicUnit.amend(facultyId, departmentId, now, command.editionDescription());
        academicUnitPort.save(amendTransition.previous());

        return AcademicUnitResult.from(academicUnitPort.save(amendTransition.next()));
    }
}
