package com.icnet.capstonehub.application.port.in.command;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AcademicUnitInitialTimelineCommand(
        UUID facultyId,
        UUID departmentId,
        LocalDateTime validAt
) {}
