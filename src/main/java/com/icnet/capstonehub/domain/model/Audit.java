package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Audit (
        User.Id createdBy,
        LocalDateTime createdAt,
        User.Id updatedBy,
        LocalDateTime updatedAt
) {}
