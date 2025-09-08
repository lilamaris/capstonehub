package com.icnet.capstonehub.application.port.in.command;

import lombok.Builder;

@Builder
public record MajorCreateCommand(
        String name
) {
}
