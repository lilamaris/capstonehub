package com.icnet.capstonehub.domain.model;

import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record AcademicUnit(
    @With Edition edition,
    @With Timeline timeline,
    Id id,
    Faculty faculty,
    Department department,
    Faculty.Id collegeId,
    Department.Id majorId
) {
    public record Id(UUID value) {
    }

    public record Transition(AcademicUnit previous, AcademicUnit next) {
    }

    public static AcademicUnit initial(Faculty.Id collegeId, Department.Id majorId, LocalDateTime txAt, LocalDateTime validAt) {
        var id = new Id(UUID.randomUUID());
        return AcademicUnit.builder()
                .id(id)
                .edition(Edition.initial(txAt))
                .timeline(Timeline.initial(Timeline.Scope.AFFILIATION, validAt))
                .collegeId(collegeId)
                .majorId(majorId)
                .build();
    }

    public Transition append(Faculty.Id collegeId, Department.Id majorId, LocalDateTime txAt, LocalDateTime validAt) {
        var splitTimeline = this.timeline.migrate(validAt);
        var previous = this.withTimeline(splitTimeline.previous());

        var newId = new Id(UUID.randomUUID());
        var newEdition = Edition.initial(txAt);
        var next = this.withTimeline(splitTimeline.next()).toBuilder()
                .id(newId)
                .edition(newEdition)
                .collegeId(collegeId)
                .majorId(majorId)
                .build();

        return new Transition(previous, next);
    }

    public Transition amend(Faculty.Id collegeId, Department.Id majorId, LocalDateTime txAt, String editionDescription) {
        var splitEdition = this.edition.migrate(txAt, editionDescription);
        var previous = this.withEdition(splitEdition.previous()).toBuilder()
                .timeline(this.timeline.expire(txAt))
                .build();

        var newId = new Id(UUID.randomUUID());
        var next = this.withEdition(splitEdition.next()).toBuilder()
                .id(newId)
                .collegeId(collegeId)
                .majorId(majorId)
                .build();

        return new Transition(previous, next);
    }
}
