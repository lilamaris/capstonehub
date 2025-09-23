package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder(toBuilder = true)
public record User(
    Id id,
    String name,
    String email,
    Role role,
    List<Account.Id> connectedAccount
) {
    public record Id(UUID value) {}
    public enum Role { STUDENT, MANAGER, ADMIN }

    public User {
        connectedAccount = Optional.ofNullable(connectedAccount).orElseGet(ArrayList::new);
    }
}
