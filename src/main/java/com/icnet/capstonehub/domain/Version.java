package com.icnet.capstonehub.domain;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record Version(
        Id id,
        Integer versionNo,
        String versionDescription,
        Period txPeriod
) {
    public record Id(UUID value) {}

    public Version closeTx(LocalDate txTo) {
        Period close = txPeriod.close(txTo);
        return this.toBuilder()
                .txPeriod(close)
                .build();
    }

    public Version next(Period txPeriod, String versionDescription) {
        return Version.builder()
                .versionNo(versionNo + 1)
                .txPeriod(txPeriod)
                .versionDescription(versionDescription)
                .build();
    }
}
