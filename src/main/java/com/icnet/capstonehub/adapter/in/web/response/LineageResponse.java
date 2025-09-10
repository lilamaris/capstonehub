package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record LineageResponse(
    UUID id,
    UUID sharedId,
    String scope,
    LocalDate validFrom,
    LocalDate validTo
) {}
