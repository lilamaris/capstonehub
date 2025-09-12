package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDate;
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

    public static Lineage initial(Scope scope, LocalDate validFrom) {
        var lineageSharedId = new SharedId(UUID.randomUUID());
        var validPeriod = Period.fromToInfinity(validFrom);
        return Lineage.builder()
                .sharedId(lineageSharedId)
                .scope(scope)
                .validPeriod(validPeriod)
                .build();
    }

    public Lineage close(LocalDate validTo) {
        if (!isHead()) {
            throw new IllegalStateException("Cannot close that already been closed.");
        }
        var closedValidPeriod = Period.pair(validPeriod.from(), validTo);

        return this.toBuilder()
                .validPeriod(closedValidPeriod)
                .build();
    }

    public Lineage next(LocalDate validFrom) {
        if (this.validPeriod.from().isAfter(validFrom)) {
            throw new IllegalArgumentException("Next lineage valid period is must after the previous valid period");
        }
        var validPeriod = Period.fromToInfinity(validFrom);

        return Lineage.builder()
                .sharedId(sharedId)
                .scope(scope)
                .validPeriod(validPeriod)
                .build();
    }

    public boolean isHead() {
        return this.validPeriod.isOpen();
    }
}
