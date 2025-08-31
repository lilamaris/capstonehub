package com.icnet.capstonehub.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Lecture {
    private final LectureId id;
    private final String name;
    private final LocalDate effectiveStartDate;
    private final LocalDate effectiveEndDate;

    public record LectureId(Long value) {
    }
}
