package com.icnet.capstonehub.application.port.in.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AcademicUnitAmendTimelineCommand(
        UUID timelineSharedId,
        UUID editionSharedId,
        UUID facultyId,
        UUID departmentId,
        String editionDescription
) {}
