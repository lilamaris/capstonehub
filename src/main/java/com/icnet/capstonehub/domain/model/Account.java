package com.icnet.capstonehub.domain.model;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Account(
    Id id,
    Type provider,
    String providerId,
    @Nullable String passwordHash,
    @With User connectedUser,
    LocalDateTime connectedAt
) {
    public record Id(UUID value) {}
    public enum Type { CREDENTIAL, GITHUB }

    @Builder
    public Account {
        if (provider.equals(Type.CREDENTIAL) && (passwordHash == null || passwordHash.isBlank())) {
            throw new IllegalArgumentException("'passwordHash' is null or blank. need valid 'passwordHash' when create 'CREDENTIALS' account");
        }
    }

    public static Account withCredential(String passwordHash, User user, LocalDateTime connectedAt) {
        var providerId = "CRED" + UUID.randomUUID();
        return Account.builder()
                .provider(Type.CREDENTIAL)
                .providerId(providerId)
                .passwordHash(passwordHash)
                .connectedUser(user)
                .connectedAt(connectedAt)
                .build();
    }

    public static Account withProvider(Type type, String providerId, User user, LocalDateTime connectedAt) {
        return Account.builder()
                .provider(type)
                .providerId(providerId)
                .connectedUser(user)
                .connectedAt(connectedAt)
                .build();
    }
}
