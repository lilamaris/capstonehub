package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record LineageResponse(
    UUID id,
    UUID lineageId,
    String lineageScope,
    LocalDate validFrom,
    LocalDate validTo
) {}
