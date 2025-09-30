package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record User(
    Id id,
    String name,
    String email,
    Role role
) {
    public record Id(UUID value) {}
    public enum Role { STUDENT, MANAGER, ADMIN }
}
