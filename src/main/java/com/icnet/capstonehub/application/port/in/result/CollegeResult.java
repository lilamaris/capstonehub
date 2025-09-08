package com.icnet.capstonehub.application.port.in.result;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CollegeResult (
    UUID id,
    String name
) {}
