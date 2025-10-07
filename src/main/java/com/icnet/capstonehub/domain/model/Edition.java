package com.icnet.capstonehub.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Edition(
        Id id,
        SharedId sharedId,
        Integer editionNo,
        String editionDescription,
        Period txPeriod
) {
    public record Id(UUID value) {}
    public record SharedId(UUID value) {}

    public record Transition(Edition previous, Edition next) {}

    public static Edition initial(LocalDateTime txFrom) {
        var id = new Id(UUID.randomUUID());
        var editionSharedId = new SharedId(UUID.randomUUID());
        var txPeriod = Period.fromToMax(txFrom);
        return Edition.builder()
                .id(id)
                .sharedId(editionSharedId)
                .editionNo(1)
                .txPeriod(txPeriod)
                .editionDescription("Initial edition")
                .build();
    }

    public Transition migrate(LocalDateTime txTo, String editionDescription) {
        if (!isHead()) throw new IllegalStateException("Cannot inherit latest edition");
        var newId = new Id(UUID.randomUUID());
        var split = this.txPeriod.splitAt(txTo);
        var previous = this.toBuilder().txPeriod(split.previous()).build();
        var next = this.toBuilder()
                .id(newId)
                .txPeriod(split.next())
                .editionNo(editionNo + 1)
                .editionDescription(editionDescription)
                .build();

        return new Transition(previous, next);
    }

    public boolean isHead() {
        return this.txPeriod.isOpen();
    }
}
