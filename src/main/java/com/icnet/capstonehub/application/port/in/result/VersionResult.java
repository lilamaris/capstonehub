package com.icnet.capstonehub.application.port.in.result;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record VersionResult(
        UUID id,
        UUID sharedId,
        Integer versionNo,
        String versionDescription,
        LocalDate txFrom,
        LocalDate txTo
) {}
