package com.icnet.capstonehub.unit.domain;

import com.icnet.capstonehub.domain.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AcademicUnitTest {
    private AcademicUnit getEntry() {
        var collegeId = new Faculty.Id(UUID.randomUUID());
        var majorId = new Department.Id(UUID.randomUUID());

        var txAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        var validAt = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        var affiliation = AcademicUnit.initial(collegeId, majorId, txAt, validAt);

        var id = new AcademicUnit.Id(UUID.randomUUID());
        return affiliation.toBuilder().id(id).build();

    }
    @Test
    void should_create_affiliation() {
        var affiliation = getEntry();

        assertThat(affiliation).isInstanceOf(AcademicUnit.class);
        assertThat(affiliation.edition()).isInstanceOf(Edition.class);
        assertThat(affiliation.timeline()).isInstanceOf(Timeline.class);
    }

    @Test
    void append_affiliation_shared_lineage_and_separate_version() {
        var affiliation = getEntry();

        var nextCollegeId = new Faculty.Id(UUID.randomUUID());
        var nextMajorId = new Department.Id(UUID.randomUUID());
        var txAt = LocalDateTime.of(2024, 7, 1, 0, 0, 0);
        var validAt = LocalDateTime.of(2024, 7, 1,  0, 0, 0);

        var appendTransition = affiliation.append(nextCollegeId, nextMajorId, txAt, validAt);
        var previous = appendTransition.previous();
        var next = appendTransition.next();

        assertThat(next).isInstanceOf(AcademicUnit.class);
        assertThat(next.id()).isNotEqualTo(previous.id());
        assertThat(next.timeline().sharedId()).isEqualTo(previous.timeline().sharedId());

        assertThat(next.timeline().isHead()).isTrue();
        assertThat(previous.timeline().isHead()).isFalse();
        assertThat(next.edition().isHead()).isTrue();
        assertThat(previous.edition().isHead()).isTrue();
    }

    @Test
    void amend_affiliation_shared_lineage_and_version_but_version_migrated() {
        var affiliation = getEntry();

        var migrateCollegeId = new Faculty.Id(UUID.randomUUID());
        var migrateMajorId = new Department.Id(UUID.randomUUID());
        var txAt = LocalDateTime.of(2024, 7, 1, 0, 0, 0);

        var versionDescription = "migrate to another faculty and department";
        var amendTransition = affiliation.amend(migrateCollegeId, migrateMajorId, txAt, versionDescription);
        var previous = amendTransition.previous();
        var next = amendTransition.next();

        assertThat(next).isInstanceOf(AcademicUnit.class);
        assertThat(previous.collegeId()).isEqualTo(affiliation.collegeId());
        assertThat(previous.majorId()).isEqualTo(affiliation.majorId());
        assertThat(previous.timeline().isHead()).isFalse();
        assertThat(previous.edition().isHead()).isFalse();

        assertThat(next.id()).isNotEqualTo(previous.id());
        assertThat(next.collegeId()).isEqualTo(migrateCollegeId);
        assertThat(next.majorId()).isEqualTo(migrateMajorId);
        assertThat(next.timeline().sharedId()).isEqualTo(previous.timeline().sharedId());
        assertThat(next.timeline().isHead()).isTrue();
        assertThat(next.edition().isHead()).isTrue();
    }
}
