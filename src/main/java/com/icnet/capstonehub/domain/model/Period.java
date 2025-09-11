package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

public record Period(LocalDate from, LocalDate to) {
    public static Period fromToInfinity(LocalDate from) {
        return Period.builder()
                .from(from)
                .build();
    }

    public static Period pair(LocalDate from, LocalDate to) {
        return Period.builder()
                .from(from)
                .to(to)
                .build();
    }

    public static boolean isOverlap(Period a, Period b) {
        LocalDate aTo = (a.to == null) ? LocalDate.MAX : a.to;
        LocalDate bTo = (b.to == null) ? LocalDate.MAX : b.to;

        return a.from.isBefore(bTo) && b.from.isBefore(aTo);
    }

    @Builder
    public Period(LocalDate from, LocalDate to) {
        this.from = Objects.requireNonNull(from, "period.from must not null");

        if (to != null && to.isBefore(from)) {
            throw new IllegalArgumentException("period.to must be >= period.from");
        }

        this.to = to;
    }

    public boolean isOpen() {
        return to == null;
    }

    public Period close(LocalDate to) {
        if (!this.isOpen()) {
            throw new IllegalStateException(String.format("Already closed period=(from=%s, to=%s)", this.from, this.to));
        }

        return Period.builder()
                .from(this.from)
                .to(to)
                .build();
    }
}
