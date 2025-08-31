package com.icnet.capstonehub.application.port.in.response;

import com.icnet.capstonehub.domain.College;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateCollegeResponse(
        Long id,
        String name,
        LocalDate effectiveStartDate,
        LocalDate effectiveEndDate
) {}
