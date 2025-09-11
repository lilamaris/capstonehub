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

    public boolean isTxValid() {
        return this.txPeriod.isOpen();
    }

    public Version closeTx(LocalDate txTo) {
        if (!this.txPeriod.isOpen()) {
            throw new IllegalStateException("Already closed version cannot be closed again");
        }
        if (!txTo.isAfter(this.txPeriod.from())) {
            throw new IllegalArgumentException("txTo must be after txFrom");
        }
        Period close = txPeriod.close(txTo);
        return this.toBuilder()
                .txPeriod(close)
                .build();
    }

    public Version next(LocalDate txFrom, String versionDescription) {
        if (!this.txPeriod.isOpen()) {
            throw new IllegalStateException("Next version required current version to be open");
        }
        if (txFrom.isBefore(this.txPeriod.from())) {
            throw new IllegalArgumentException("next.txFrom cannot be before current.txFrom");
        }
        Period txPeriod = Period.fromToInfinity(txFrom);
        return Version.builder()
                .sharedId(sharedId)
                .versionNo(versionNo + 1)
                .txPeriod(txPeriod)
                .versionDescription(versionDescription)
                .build();
    }
}
