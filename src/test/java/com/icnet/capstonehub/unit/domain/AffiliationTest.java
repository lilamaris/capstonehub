package com.icnet.capstonehub.unit.domain;

import com.icnet.capstonehub.domain.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AffiliationTest {
    private Affiliation getEntry() {
        var collegeId = new College.Id(UUID.randomUUID());
        var majorId = new Major.Id(UUID.randomUUID());

        var txAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        var validAt = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        var affiliation = Affiliation.initial(collegeId, majorId, txAt, validAt);

        var id = new Affiliation.Id(UUID.randomUUID());
        return affiliation.toBuilder().id(id).build();

    }
    @Test
    void should_create_affiliation() {
        var affiliation = getEntry();

        assertThat(affiliation).isInstanceOf(Affiliation.class);
        assertThat(affiliation.version()).isInstanceOf(Version.class);
        assertThat(affiliation.lineage()).isInstanceOf(Lineage.class);
    }

    @Test
    void append_affiliation_shared_lineage_and_separate_version() {
        var affiliation = getEntry();

        var nextCollegeId = new College.Id(UUID.randomUUID());
        var nextMajorId = new Major.Id(UUID.randomUUID());
        var txAt = LocalDateTime.of(2024, 7, 1, 0, 0, 0);
        var validAt = LocalDateTime.of(2024, 7, 1,  0, 0, 0);

        var appendTransition = affiliation.append(nextCollegeId, nextMajorId, txAt, validAt);
        var previous = appendTransition.previous();
        var next = appendTransition.next();

        assertThat(next).isInstanceOf(Affiliation.class);
        assertThat(next.id()).isNotEqualTo(previous.id());
        assertThat(next.lineage().sharedId()).isEqualTo(previous.lineage().sharedId());

        assertThat(next.lineage().isHead()).isTrue();
        assertThat(previous.lineage().isHead()).isFalse();
        assertThat(next.version().isHead()).isTrue();
        assertThat(previous.version().isHead()).isTrue();
    }

    @Test
    void amend_affiliation_shared_lineage_and_version_but_version_migrated() {
        var affiliation = getEntry();

        var migrateCollegeId = new College.Id(UUID.randomUUID());
        var migrateMajorId = new Major.Id(UUID.randomUUID());
        var txAt = LocalDateTime.of(2024, 7, 1, 0, 0, 0);

        var versionDescription = "migrate to another college and major";
        var amendTransition = affiliation.amend(migrateCollegeId, migrateMajorId, txAt, versionDescription);
        var previous = amendTransition.previous();
        var next = amendTransition.next();

        assertThat(next).isInstanceOf(Affiliation.class);
        assertThat(previous.collegeId()).isEqualTo(affiliation.collegeId());
        assertThat(previous.majorId()).isEqualTo(affiliation.majorId());
        assertThat(previous.lineage().isHead()).isFalse();
        assertThat(previous.version().isHead()).isFalse();

        assertThat(next.id()).isNotEqualTo(previous.id());
        assertThat(next.collegeId()).isEqualTo(migrateCollegeId);
        assertThat(next.majorId()).isEqualTo(migrateMajorId);
        assertThat(next.lineage().sharedId()).isEqualTo(previous.lineage().sharedId());
        assertThat(next.lineage().isHead()).isTrue();
        assertThat(next.version().isHead()).isTrue();
    }
}
