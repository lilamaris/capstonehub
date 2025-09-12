package com.icnet.capstonehub.unit.domain;

import com.icnet.capstonehub.domain.model.Lineage;
import com.icnet.capstonehub.domain.model.Period;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LineageTest {
    @Test
    void should_create_new_lineage() {
        var validFrom = LocalDate.of(2024, 1, 1);
        Lineage l1 = Lineage.initial(Lineage.Scope.AFFILIATION, validFrom);

        assertThat(l1).isInstanceOf(Lineage.class);
        assertThat(l1.sharedId()).isNotNull();
        assertThat(l1.scope()).isEqualTo(Lineage.Scope.AFFILIATION);
        assertThat(l1.isHead()).isTrue();
    }

    @Test
    void should_create_next_lineage() {
        var validFrom = LocalDate.of(2024, 1, 1);
        Lineage l1 = Lineage.initial(Lineage.Scope.AFFILIATION, validFrom);

        var nextValidFrom = LocalDate.of(2024, 6, 1);
        Lineage l2 = l1.next(nextValidFrom);

        assertThat(l2).isInstanceOf(Lineage.class);
    }

    @Test
    void should_next_lineage_retain_previous_sharedId() {
        var validFrom = LocalDate.of(2024, 1, 1);
        Lineage l1 = Lineage.initial(Lineage.Scope.AFFILIATION, validFrom);

        var nextValidFrom = LocalDate.of(2024, 6, 1);
        Lineage l2 = l1.next(nextValidFrom);

        assertThat(l2.sharedId()).isEqualTo(l1.sharedId());
    }

    @Test
    void should_next_lineage_is_must_after_the_previous_validPeriod() {
        var validFrom = LocalDate.of(2024, 6, 1);
        Lineage l1 = Lineage.initial(Lineage.Scope.AFFILIATION, validFrom);

        var nextValidFrom = LocalDate.of(2024, 3, 1);
        assertThatThrownBy(
                () -> l1.next(nextValidFrom)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_close_lineage_when_initial_next_lineage() {
        var validFrom = LocalDate.of(2024, 3, 1);
        Lineage l1 = Lineage.initial(Lineage.Scope.AFFILIATION, validFrom);

        var nextValidFrom = LocalDate.of(2024, 3, 1);
        l1 = l1.close(nextValidFrom);
        Lineage l2 = l1.next(nextValidFrom);

        assertThat(l1.isHead()).isFalse();
        assertThat(l2.isHead()).isTrue();
        assertThat(Period.isOverlap(l1.validPeriod(), l2.validPeriod())).isFalse();
    }

    @Test
    void should_throw_except_when_close_before_from() {
        var validFrom = LocalDate.of(2024, 3, 1);
        Lineage l1 = Lineage.initial(Lineage.Scope.AFFILIATION, validFrom);

        var nextValidFrom = LocalDate.of(2024, 2, 1);
        assertThatThrownBy(
                () -> l1.close(nextValidFrom)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_throw_except_when_initial_next_that_already_closed() {
        var validFrom = LocalDate.of(2024, 3, 1);
        Lineage l1 = Lineage.initial(Lineage.Scope.AFFILIATION, validFrom);

        var nextValidFrom = LocalDate.of(2024, 3, 1);
        Lineage closed = l1.close(nextValidFrom);

        assertThatThrownBy(
                () -> closed.close(nextValidFrom.plusDays(1))
        ).isInstanceOf(IllegalStateException.class);
    }
}
