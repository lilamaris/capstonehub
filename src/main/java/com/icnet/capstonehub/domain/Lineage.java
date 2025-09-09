package com.icnet.capstonehub.domain;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record Lineage(
        Id id,
        LineageId lineageId,
        Scope scope,
        Period validPeriod
) {
    public record Id(UUID value) {}
    public record LineageId(UUID value) {}
    public enum Scope { AFFILIATION, COURSE }

    public Lineage closeValid(LocalDate validTo) {
        Period close = validPeriod.close(validTo);
        return this.toBuilder()
                .validPeriod(close)
                .build();
    }

    public Lineage next(Period validPeriod) {
        return Lineage.builder()
                .lineageId(lineageId)
                .scope(scope)
                .validPeriod(validPeriod)
                .build();
    }
}
