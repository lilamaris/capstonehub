package com.icnet.capstonehub.domain;

import lombok.Builder;
import lombok.With;

import java.util.UUID;

@Builder(toBuilder = true)
public record Affiliation (
    @With Version version,
    @With Lineage lineage,
    Id id,
    Major major,
    College college
) {
    public record Id(UUID value) {}
}
