package com.icnet.capstonehub.application.port.in.result;

import com.icnet.capstonehub.domain.model.User;
import lombok.Builder;

@Builder
public record UserResult(
    User.Id id,
    String name,
    String email,
    String role
) {
    public static UserResult from(User domain) {
        return UserResult.builder()
                .id(domain.id())
                .name(domain.name())
                .email(domain.email())
                .role(domain.role().name())
                .build();
    }
}
