package com.icnet.capstonehub.application.port.in.command;

import lombok.Builder;

@Builder
public record SignupCredentialCommand(
        String name,
        String email,
        String passwordHash
) { }