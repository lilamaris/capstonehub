package com.icnet.capstonehub.common;

import com.icnet.capstonehub.domain.model.Period;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PeriodRuleTest {

    @Test
    void should_create_period() {
        LocalDate from = LocalDate.of(2024, 1, 31);
        LocalDate to = LocalDate.of(2024, 6, 1);

        Period period = Period.pair(from, to);
        assertThat(period.from()).isEqualTo(from);
        assertThat(period.to()).isEqualTo(to);
    }

    @Test
    void should_create_period_half_opened() {
        LocalDate from = LocalDate.of(2024, 6, 1);
        LocalDate to = LocalDate.of(2024, 6, 1);

        Period period = Period.pair(from, to);

        assertThat(period).isInstanceOf(Period.class);
        assertThat(period.from()).isEqualTo(from);
        assertThat(period.to()).isEqualTo(to);
    }

    @Test
    void should_throw_invalid_period() {
        LocalDate from = LocalDate.of(2024, 6, 1);
        LocalDate to = LocalDate.of(2024, 1, 31);

        assertThatThrownBy(
                () -> Period.pair(from, to)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_detect_overlap() {
        Period a = Period.pair(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31)
        );

        Period b = Period.pair(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 1)
        );

        assertThat(Period.isOverlap(a, b)).isTrue();
    }

    @Test
    void should_not_detect_overlap() {
        Period a = Period.pair(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 6, 1)
        );

        Period b = Period.pair(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 12, 31)
        );

        assertThat(Period.isOverlap(a, b)).isFalse();
    }

    @Test
    void setInfinityIfToIsNull() {
        Period a = Period.fromToInfinity(
                LocalDate.of(2024, 1, 1)
        );

        Period b = Period.pair(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31)
        );

        assertThat(Period.isOverlap(a, b)).isTrue();
    }

    @Test
    void should_detect_is_open_when_to_is_null() {
        LocalDate from = LocalDate.of(2024, 1, 1);

        Period period = Period.fromToInfinity(from);

        assertThat(period.isOpen()).isTrue();
    }

    @Test
    void should_detect_is_close_when_to_is_not_null() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 5);

        Period period = Period.pair(from, to);

        assertThat(period.isOpen()).isFalse();
    }

    @Test
    void should_create_close_period() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate closeTargetDate = LocalDate.of(2025, 1, 1);

        Period period = Period.fromToInfinity(from);

        Period closedPeriod = period.close(closeTargetDate);

        assertThat(period.isOpen()).isTrue();
        assertThat(closedPeriod.isOpen()).isFalse();

        assertThat(period.to()).isNull();
        assertThat(closedPeriod.to()).isEqualTo(closeTargetDate);
    }

    @Test
    void should_throw_except_when_try_close_already_closed() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2025, 1, 1);
        LocalDate anotherTo = LocalDate.of(2025, 2, 1);

        Period period = Period.pair(from, to);

        assertThat(period.isOpen()).isFalse();
        assertThatThrownBy(
                () -> period.close(anotherTo)
        ).isInstanceOf(IllegalStateException.class);
    }
}
