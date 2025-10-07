package com.icnet.capstonehub.domain.model;

import lombok.With;

import java.util.UUID;

public record Course(
   @With Edition edition,
   @With Timeline timeline,
   Id id,
   String name,
   Integer credit
) {
    public record Id(UUID value) {}

}
