package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.AcademicUnitAmendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitAppendTimelineCommand;
import com.icnet.capstonehub.application.port.in.command.AcademicUnitInitialTimelineCommand;
import com.icnet.capstonehub.application.port.in.result.AcademicUnitResult;

public interface AcademicUnitCommandUseCase {
    AcademicUnitResult.Command initialTimeline(AcademicUnitInitialTimelineCommand command);
    AcademicUnitResult.Command appendTimeline(AcademicUnitAppendTimelineCommand command);
    AcademicUnitResult.Command amendTimeline(AcademicUnitAmendTimelineCommand command);
}
