package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record User(
    Id id,
    String name,
    String email,
    Role role,
    List<Account.Id> connectedAccount
) {
    public record Id(UUID value) {}
    public enum Role { STUDENT, MANAGER, ADMIN }
}
