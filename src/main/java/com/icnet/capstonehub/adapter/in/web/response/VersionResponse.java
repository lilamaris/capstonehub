package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record VersionResponse(
    UUID id,
    UUID lineageId,
    String lineageScope,
    LocalDate txFrom,
    LocalDate txTo,
    Integer versionNo,
    String versionDescription
) {}
