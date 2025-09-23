package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse (
        UUID id,
        String name,
        String email,
        String role
) {}
