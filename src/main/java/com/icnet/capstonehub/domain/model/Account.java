package com.icnet.capstonehub.domain.model;

import jakarta.annotation.Nullable;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public record Account(
    Type provider,
    String providerId,
    @Nullable String passwordHash,
    User.Id connectedUserId,
    LocalDateTime connectedAt
) {
    public enum Type { CREDENTIAL, GITHUB }

    @Builder
    public Account {
        if (provider.equals(Type.CREDENTIAL) && (passwordHash == null || passwordHash.isBlank())) {
            throw new IllegalArgumentException("'passwordHash' is null or blank. need valid 'passwordHash' when create 'CREDENTIALS' account");
        }
    }

    public static Account withCredential(String passwordHash, User.Id userId, LocalDateTime connectedAt) {
        var providerId = "CRED" + UUID.randomUUID();
        return Account.builder()
                .provider(Type.CREDENTIAL)
                .providerId(providerId)
                .passwordHash(passwordHash)
                .connectedUserId(userId)
                .connectedAt(connectedAt)
                .build();
    }

    public static Account withProvider(Type type, String providerId, User.Id userId, LocalDateTime connectedAt) {
        return Account.builder()
                .provider(type)
                .providerId(providerId)
                .connectedUserId(userId)
                .connectedAt(connectedAt)
                .build();
    }
}
