package com.icnet.capstonehub.application.port.in.command;

public record UpdateUserCommand(
        String name,
        String email
) {}