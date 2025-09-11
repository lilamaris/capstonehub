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

    public Lineage closeValid(LocalDate validTo) {
        Period close = validPeriod.close(validTo);
        return this.toBuilder()
                .validPeriod(close)
                .build();
    }

    public Lineage next(Period validPeriod) {
        return Lineage.builder()
                .sharedId(sharedId)
                .scope(scope)
                .validPeriod(validPeriod)
                .build();
    }
}
