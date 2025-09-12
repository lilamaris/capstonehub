package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record LineageResponse(
    UUID id,
    UUID sharedId,
    String scope,
    LocalDateTime validFrom,
    LocalDateTime validTo
) {}
