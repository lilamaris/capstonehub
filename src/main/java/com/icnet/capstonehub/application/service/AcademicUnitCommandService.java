package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.AcademicUnitCommandUseCase;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAmendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAppendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitInitialTimelineCommand;
import com.icnet.capstonehub.application.port.in.result.AcademicUnitResult;
import com.icnet.capstonehub.application.port.out.*;
import com.icnet.capstonehub.domain.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AcademicUnitCommandService implements AcademicUnitCommandUseCase {
    private final AcademicUnitPort academicUnitPort;

    @Override
    @Transactional
    public AcademicUnitResult.Command initialTimeline(AcademicUnitInitialTimelineCommand command) {
        var now = LocalDateTime.now();
        var facultyId = new Faculty.Id(command.facultyId());
        var departmentId = new Department.Id(command.departmentId());

        var initial = AcademicUnit.initial(facultyId, departmentId, now, command.validAt());
        return AcademicUnitResult.Command.from(academicUnitPort.save(initial));
    }

    @Override
    public AcademicUnitResult.Command appendTimeline(AcademicUnitAppendTimelineCommand command) {
        var now = LocalDateTime.now();
        var facultyId = Faculty.Id.from(command.facultyId());
        var departmentId = Department.Id.from(command.departmentId());
        var sharedId = Timeline.SharedId.from(command.timelineSharedId());

        var head = academicUnitPort.getHeadOfTimeline(sharedId).orElseThrow(EntityNotFoundException::new);
        var appendTransition = head.append(facultyId, departmentId, now, command.validAt());

        academicUnitPort.save(appendTransition.previous());
        return AcademicUnitResult.Command.from(academicUnitPort.save(appendTransition.next()));
    }

    @Override
    public AcademicUnitResult.Command amendTimeline(AcademicUnitAmendTimelineCommand command) {
        var now = LocalDateTime.now();
        var facultyId = Faculty.Id.from(command.facultyId());
        var departmentId = Department.Id.from(command.departmentId());
        var sharedId = Timeline.SharedId.from(command.timelineSharedId());

        return null;
//        var headAcademicUnit = academicUnitPort.getSnapshotOfRecord(timelineSharedId, editionSharedId, now)
//                .orElseThrow(EntityNotFoundException::new);
//        var amendTransition = headAcademicUnit.amend(facultyId, departmentId, now, command.editionDescription());
//        academicUnitPort.save(amendTransition.previous());
//
//        return AcademicUnitResult.Command.from(academicUnitPort.save(amendTransition.next()));
    }
}
