package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record Version(
        Id id,
        SharedId sharedId,
        Integer versionNo,
        String versionDescription,
        Period txPeriod
) {
    public record Id(UUID value) {}
    public record SharedId(UUID value) {}

    public static Version initial(LocalDate txFrom) {
        var versionSharedId = new SharedId(UUID.randomUUID());
        var txPeriod = Period.fromToInfinity(txFrom);
        return Version.builder()
                .sharedId(versionSharedId)
                .versionNo(1)
                .txPeriod(txPeriod)
                .versionDescription("Initial version")
                .build();
    }
    public Version close(LocalDate txTo) {
        if (!this.isHead()) {
            throw new IllegalStateException("Already closed version cannot be closed again");
        }
        var closedTxPeriod = Period.pair(txPeriod.from(), txTo);
        return this.toBuilder()
                .txPeriod(closedTxPeriod)
                .build();
    }

    public Version next(LocalDate txFrom, String versionDescription) {
        if (this.txPeriod.from().isAfter(txFrom)) {
            throw new IllegalStateException("Next version tx period is must after the previous tx period");
        }
        var txPeriod = Period.fromToInfinity(txFrom);

        return Version.builder()
                .sharedId(sharedId)
                .versionNo(versionNo + 1)
                .txPeriod(txPeriod)
                .versionDescription(versionDescription)
                .build();
    }

    public boolean isHead() {
        return this.txPeriod.isOpen();
    }

}
