package com.icnet.capstonehub.domain.model;

import java.util.UUID;

public record Course(
   Id id,
   String name
) {
    public record Id(UUID value) {}
}
