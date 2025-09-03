package com.icnet.capstonehub.application.port.out.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CollegeResponse(
        Long id,
        String name,
        LocalDate effectiveStartDate,
        LocalDate effectiveEndDate
) {}
