package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.UserResult;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse (
        UUID id,
        String name,
        String email,
        String role
) {
    public static UserResponse from(UserResult result) {
        return UserResponse.builder()
                .id(result.id())
                .name(result.name())
                .email(result.email())
                .role(result.role())
                .build();
    }
}
