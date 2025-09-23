package com.icnet.capstonehub.application.port.in.command;

public record CreateUserCommand (
        String name,
        String email,
        String password
) {}