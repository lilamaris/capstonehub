package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.AcademicUnitAmendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAppendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitInitialTimelineCommand;
import com.icnet.capstonehub.application.port.in.result.AcademicUnitResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AcademicUnitUseCase {
    List<AcademicUnitResult> getAllAcademicUnitByUser(UUID userId);
    List<AcademicUnitResult> getAcademicUnitTimeline(UUID lineageSharedId);
    List<AcademicUnitResult> getAcademicUnitTimeline(UUID lineageSharedId, LocalDateTime txAt);
    AcademicUnitResult initialAcademicUnitTimeline(AcademicUnitInitialTimelineCommand command);
    AcademicUnitResult appendAcademicUnitTimeline(AcademicUnitAppendTimelineCommand command);
    AcademicUnitResult amendAcademicUnitTimeline(AcademicUnitAmendTimelineCommand command);
}
