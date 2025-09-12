package com.icnet.capstonehub.domain.model;

import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record Affiliation (
    @With Version version,
    @With Lineage lineage,
    Id id,
    College college,
    Major major,
    College.Id collegeId,
    Major.Id majorId
) {
    public record Id(UUID value) {
    }

    public record Transition(Affiliation previous, Affiliation next) {
    }

    public static Affiliation initial(College.Id collegeId, Major.Id majorId, LocalDateTime txAt, LocalDateTime validAt) {
        var id = new Id(UUID.randomUUID());
        return Affiliation.builder()
                .id(id)
                .version(Version.initial(txAt))
                .lineage(Lineage.initial(Lineage.Scope.AFFILIATION, validAt))
                .collegeId(collegeId)
                .majorId(majorId)
                .build();
    }

    public Transition append(College.Id collegeId, Major.Id majorId, LocalDateTime txAt, LocalDateTime validAt) {
        var splitLineage = this.lineage.migrate(validAt);
        var previous = this.withLineage(splitLineage.previous());

        var newId = new Id(UUID.randomUUID());
        var newVersion = Version.initial(txAt);
        var next = this.withLineage(splitLineage.next()).toBuilder()
                .id(newId)
                .version(newVersion)
                .collegeId(collegeId)
                .majorId(majorId)
                .build();

        return new Transition(previous, next);
    }

    public Transition amend(College.Id collegeId, Major.Id majorId, LocalDateTime txAt, String versionDescription) {
        var splitVersion = this.version.migrate(txAt, versionDescription);
        var previous = this.withVersion(splitVersion.previous()).toBuilder()
                .lineage(this.lineage.expire(txAt))
                .build();

        var newId = new Id(UUID.randomUUID());
        var next = this.withVersion(splitVersion.next()).toBuilder()
                .id(newId)
                .collegeId(collegeId)
                .majorId(majorId)
                .build();

        return new Transition(previous, next);
    }
}
