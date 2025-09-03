package com.icnet.capstonehub.application.port.out.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AffiliationResponse(
        Long id,
        College college,
        Major major,
        LocalDate effectiveStartDate,
        LocalDate effectiveEndDate
) {
    private record College (Long id, String name) {}
    private record Major (Long id, String name) {}
}
