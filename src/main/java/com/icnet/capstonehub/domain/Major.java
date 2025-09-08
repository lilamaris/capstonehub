package com.icnet.capstonehub.domain;

import lombok.*;

import java.util.UUID;

@Builder
public record Major(Id id, String name) {
    public record Id(UUID value) {}
}
