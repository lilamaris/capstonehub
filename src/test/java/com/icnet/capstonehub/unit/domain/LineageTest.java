package com.icnet.capstonehub.unit.domain;

import com.icnet.capstonehub.domain.model.Lineage;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LineageTest {
    @Test
    void should_create_new_lineage() {
        var validFrom = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        Lineage l1 = Lineage.initial(Lineage.Scope.AFFILIATION, validFrom);

        assertThat(l1).isInstanceOf(Lineage.class);
        assertThat(l1.id()).isNotNull();
        assertThat(l1.sharedId()).isNotNull();
        assertThat(l1.scope()).isEqualTo(Lineage.Scope.AFFILIATION);
        assertThat(l1.isHead()).isTrue();
    }

    @Test
    void should_create_next_lineage() {
        var validFrom = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        Lineage l1 = Lineage.initial(Lineage.Scope.AFFILIATION, validFrom);

        var validTo = LocalDateTime.of(2024, 7, 1, 0, 0, 0);
        Lineage.Transition transition = l1.migrate(validTo);

        var previous = transition.previous();
        var next = transition.next();

        assertThat(next).isInstanceOf(Lineage.class);
        assertThat(previous.id()).isNotEqualTo(next.id());
        assertThat(previous.sharedId()).isEqualTo(next.sharedId());
        assertThat(previous.isHead()).isFalse();

        assertThat(next.isHead()).isTrue();

        assertThat(previous.validPeriod().isOverlap(next.validPeriod())).isFalse();
        assertThat(previous.validPeriod().to()).isEqualTo(next.validPeriod().from());
    }
}
