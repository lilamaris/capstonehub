package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record RefreshToken (
        Id id,
        User.Id userId,
        UUID token,
        Instant expiredAt
) {
    public record Id (UUID value) {}
}
