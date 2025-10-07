package com.icnet.capstonehub.application.port.in.command;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AcademicUnitAppendTimelineCommand(
        UUID timelineSharedId,
        UUID facultyId,
        UUID departmentId,
        LocalDateTime validAt,
        String editionDescription
) {}
