package com.icnet.capstonehub.domain;

import lombok.*;

import java.time.LocalDate;

@Builder
public record Lecture(LectureId id, String name, LocalDate effectiveStartDate, LocalDate effectiveEndDate) {
    public record LectureId(Long value) {
    }
}
