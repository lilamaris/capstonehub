package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
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

    public record Transition(Version previous, Version next) {}

    public static Version initial(LocalDateTime txFrom) {
        var id = new Id(UUID.randomUUID());
        var versionSharedId = new SharedId(UUID.randomUUID());
        var txPeriod = Period.fromToMax(txFrom);
        return Version.builder()
                .id(id)
                .sharedId(versionSharedId)
                .versionNo(1)
                .txPeriod(txPeriod)
                .versionDescription("Initial version")
                .build();
    }

    public Transition migrate(LocalDateTime txTo, String versionDescription) {
        if (!isHead()) throw new IllegalStateException("Cannot inherit latest version");
        var newId = new Id(UUID.randomUUID());
        var split = this.txPeriod.splitAt(txTo);
        var previous = this.toBuilder().txPeriod(split.previous()).build();
        var next = this.toBuilder()
                .id(newId)
                .txPeriod(split.next())
                .versionNo(versionNo + 1)
                .versionDescription(versionDescription)
                .build();

        return new Transition(previous, next);
    }

    public boolean isHead() {
        return this.txPeriod.isOpen();
    }
}
