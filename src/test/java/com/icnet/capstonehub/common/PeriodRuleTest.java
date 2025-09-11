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

        Period period = new Period(from, to);

        assertThat(period).isInstanceOf(Period.class);
        assertThat(period.from()).isEqualTo(from);
        assertThat(period.to()).isEqualTo(to);
    }
    @Test
    void should_create_period_fromToInfinity() {
        LocalDate from = LocalDate.of(2024, 1, 31);

        Period period = Period.fromToInfinity(from);

        assertThat(period).isInstanceOf(Period.class);
        assertThat(period.from()).isEqualTo(from);
        assertThat(period.to()).isNull();
    }
    @Test
    void should_create_period_pair() {
        LocalDate from = LocalDate.of(2024, 1, 31);
        LocalDate to = LocalDate.of(2024, 6, 1);

        Period period = Period.pair(from, to);

        assertThat(period).isInstanceOf(Period.class);
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
}
