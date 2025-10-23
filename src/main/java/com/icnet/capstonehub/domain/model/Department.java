package com.icnet.capstonehub.domain.model;

import lombok.*;

import java.util.UUID;

@Builder
public record Department(Id id, String name, Audit audit) {
    public record Id(UUID value) {
        public static Id from(UUID value) {
            return new Id(value);
        }
    }
}
