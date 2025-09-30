package com.icnet.capstonehub.domain.model;

import lombok.*;

import java.util.UUID;

@Builder
public record College(Id id, String name, Audit audit) {
    public record Id(UUID value) {}
}
