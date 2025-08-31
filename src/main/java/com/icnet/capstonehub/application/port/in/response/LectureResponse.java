package com.icnet.capstonehub.application.port.in.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record LectureResponse(
        Long id,
        String name,
        LocalDate effectiveStartDate,
        LocalDate effectiveEndDate
) {}
