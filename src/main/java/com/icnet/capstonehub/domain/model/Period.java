package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Optional;

public record Period(LocalDateTime from, LocalDateTime to) {
    public record Split(Period previous, Period next) {}

    @Builder
    public Period {
        var f = Optional.ofNullable(from)
                .orElseThrow(() -> new NullPointerException("from is must be not null"));
        var t = Optional.ofNullable(to)
                .orElseThrow(() -> new NullPointerException("from is must be not null"));
        if (t.isBefore(f)) {
            throw new IllegalArgumentException("'to' is must be after than 'from'");
        }

    }

    public static Period fromToInfinity(LocalDateTime from) {
        return Period.builder()
                .from(from)
                .to(LocalDateTime.MAX)
                .build();
    }

    public static Period pair(LocalDateTime from, LocalDateTime to) {
        if (from.isEqual(to)) throw new IllegalArgumentException("cannot create a pair where 'from' and 'to' value are the same. Instead, use pick(LocalDateTime from).");
        return Period.builder()
                .from(from)
                .to(to)
                .build();
    }

    public static Period pick(LocalDateTime from) {
        return pair(from, from.plusNanos(1));
    }

    public boolean isOverlap(Period b) {
        return from.isBefore(b.to) && to.isAfter(b.from);
    }

    public boolean isOpen() {
        return to == LocalDateTime.MAX;
    }

    public Split splitAt(LocalDateTime at) {
        var previous = Period.pair(from, at);
        var next = Period.pair(at, to);

        return new Split(previous, next);
    }
}
