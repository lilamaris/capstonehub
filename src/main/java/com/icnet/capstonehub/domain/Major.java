package com.icnet.capstonehub.domain;

import lombok.*;

import java.time.LocalDate;

@Builder
public record Major(MajorId id, String name, LocalDate effectiveStartDate, LocalDate effectiveEndDate) {
    public record MajorId(Long value) {
    }
}
