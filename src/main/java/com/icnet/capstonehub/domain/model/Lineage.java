package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Lineage(
        Id id,
        SharedId sharedId,
        Scope scope,
        Period validPeriod
) {
    public record Id(UUID value) {}
    public record SharedId(UUID value) {}
    public enum Scope { AFFILIATION, COURSE }

    public record Transition(Lineage previous, Lineage next) {}

    public static Lineage initial(Scope scope, LocalDateTime validFrom) {
        var id = new Id(UUID.randomUUID());
        var lineageSharedId = new SharedId(UUID.randomUUID());
        var validPeriod = Period.fromToMax(validFrom);
        return Lineage.builder()
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
}
