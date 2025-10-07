package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Timeline(
        Id id,
        SharedId sharedId,
        Scope scope,
        Period validPeriod
) {
    public record Id(UUID value) {}
    public record SharedId(UUID value) {}
    public enum Scope { AFFILIATION, COURSE }

    public record Transition(Timeline previous, Timeline next) {}

    public static Timeline initial(Scope scope, LocalDateTime validFrom) {
        var id = new Id(UUID.randomUUID());
        var lineageSharedId = new SharedId(UUID.randomUUID());
        var validPeriod = Period.fromToMax(validFrom);
        return Timeline.builder()
                .id(id)
                .sharedId(lineageSharedId)
                .scope(scope)
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
