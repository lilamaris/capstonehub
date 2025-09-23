package com.icnet.capstonehub.application.port.in.result;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResult(
    UUID id,
    String name,
    String email,
    String role
) {}
