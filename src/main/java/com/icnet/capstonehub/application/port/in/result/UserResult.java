package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.User;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResult(
    UUID id,
    String name,
    String email,
    String role
) {
    public static UserResult from(User domain) {
        return UserResult.builder()
                .id(domain.id().value())
                .name(domain.name())
                .email(domain.email())
                .role(domain.role().name())
                .build();
    }
}
