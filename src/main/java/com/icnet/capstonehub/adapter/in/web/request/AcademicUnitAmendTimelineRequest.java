package com.icnet.capstonehub.adapter.in.web.request;

import java.util.UUID;

public record AcademicUnitAmendTimelineRequest(
        UUID facultyId,
        UUID departmentId,
        String editionDescription
) {}
