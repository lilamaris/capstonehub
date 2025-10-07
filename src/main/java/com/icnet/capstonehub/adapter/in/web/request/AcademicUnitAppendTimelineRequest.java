package com.icnet.capstonehub.adapter.in.web.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record AcademicUnitAppendTimelineRequest(
        UUID facultyId,
        UUID departmentId,
        LocalDateTime validAt
) {}