package com.icnet.capstonehub.domain;

import lombok.*;

import java.time.LocalDate;

@Builder
public record College(CollegeId id, String name, LocalDate effectiveStartDate, LocalDate effectiveEndDate) {
    public record CollegeId(Long value) {}
}
