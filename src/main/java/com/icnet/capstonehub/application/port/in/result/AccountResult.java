package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.Account;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AccountResult(
    UUID id,
    Account.Type provider,
    String providerId,
    String passwordHash,
    UserResult user
) {
    public static AccountResult from(Account domain) {
        return AccountResult.builder()
                .id(domain.id().value())
                .provider(domain.provider())
                .providerId(domain.providerId())
                .passwordHash(domain.passwordHash())
                .user(UserResult.from(domain.connectedUser()))
                .build();
    }
}
