package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Timeline(
        Id id,
        SharedId sharedId,
        Period validPeriod
) {
    public record Id(UUID value) {
        public static Id from(UUID value) {
            return new Id(value);
        }
    }
    public record SharedId(UUID value) {
        public static SharedId from(UUID value) {
            return new SharedId(value);
        }
    }
    public record Transition(Timeline previous, Timeline next) {}

    public static Timeline initial(LocalDateTime validFrom) {
        var sharedId = new SharedId(UUID.randomUUID());
        var validPeriod = Period.fromToMax(validFrom);
        return Timeline.builder()
                .sharedId(sharedId)
                .validPeriod(validPeriod)
                .build();
    }

    public boolean isHead() {
        return this.validPeriod.isOpen();
    }

    public Transition migrate(LocalDateTime validTo) {
        var newId = new Id(UUID.randomUUID());
        var split = this.validPeriod.splitAt(validTo);
        var previous = this.toBuilder().validPeriod(split.previous()).build();
        var next = this.toBuilder()
                .id(newId)
                .validPeriod(split.next())
                .build();

        return new Transition(previous, next);
    }

    public Timeline expire(LocalDateTime validTo) {
        var closedPeriod = Period.pair(this.validPeriod().from(), validTo);

        return this.toBuilder()
                .validPeriod(closedPeriod)
                .build();
    }
}
