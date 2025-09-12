package com.icnet.capstonehub.unit.domain;

import com.icnet.capstonehub.domain.model.Period;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PeriodTest {

    private static void assertOverlap(Period a, Period b) {
        assertThat(a.isOverlap(b)).isTrue();
        assertThat(b.isOverlap(a)).isTrue();
    }

    private static void assertNoOverlap(Period a, Period b) {
        assertThat(a.isOverlap(b)).isFalse();
        assertThat(b.isOverlap(a)).isFalse();
    }

    @Test
    void should_create_period() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0,  0);
        LocalDateTime to = LocalDateTime.of(2024, 6, 1, 0, 0, 0);

        Period period = Period.pair(from, to);
        assertThat(period.from()).isEqualTo(from);
        assertThat(period.to()).isEqualTo(to);
    }

    @Test
    void should_create_period_half_opened() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0,  0);
        LocalDateTime to = LocalDateTime.of(2024, 6, 1, 0, 0, 0);

        Period period = Period.pair(from, to);

        assertThat(period).isInstanceOf(Period.class);
        assertThat(period.from()).isEqualTo(from);
        assertThat(period.to()).isEqualTo(to);
    }

    @Test
    void halfOpen_overlap_rules() {
        LocalDateTime beforeFrom = LocalDateTime.of(2024, 1, 1, 0, 0,  0);
        LocalDateTime from = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        LocalDateTime mid = LocalDateTime.of(2024, 6, 15, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 7, 1, 0, 0, 0);
        LocalDateTime afterTo = LocalDateTime.of(2024, 12, 1, 0, 0, 0);

        Period ref = Period.pair(from, to);

        // bf- - f - mid - t - - at
        // - - - [ - Ref - ) - - -
        // - - - [ - CMP - ) - - -
        assertOverlap(ref, Period.pair(from, to));

        // bf- - f - mid - t - - at
        // - - - [ - Ref - ) - - -
        // [ CMP ) - - - - [ CMP )
        assertNoOverlap(ref, Period.pair(beforeFrom, from));
        assertNoOverlap(ref, Period.pair(to, afterTo));

        // bf- - f - mid - t - - at
        // - - - [ - Ref - ) - - -
        // - - - [)- - - -[) - - -
        assertOverlap(ref, Period.pick(from));
        assertNoOverlap(ref, Period.pick(to));

        // bf- - f - mid - t - - at
        // - - - [ - Ref - ) - - -
        // [ - CMP - ) [ - CMP - )
        assertOverlap(ref, Period.pair(beforeFrom, mid));
        assertOverlap(ref, Period.pair(mid, afterTo));
    }

    @Test
    void contains_variations_should_overlap() {
        LocalDateTime from = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 7, 1, 0, 0, 0);

        Period ref = Period.pair(from, to);

        // complete contains
        assertOverlap(ref, Period.pair(from.plusDays(1), to.minusDays(1)));

        // same from, after to
        assertOverlap(ref, Period.pair(from, to.plusDays(1)));

        // before from, same to
        assertOverlap(ref, Period.pair(from.minusDays(1), to));
    }

    @Test
    void open_end_should_behave_as_max() {
        LocalDateTime from = LocalDateTime.of(2024, 6, 1, 0, 0);
        LocalDateTime later = LocalDateTime.of(2024, 8, 1, 0, 0);
        LocalDateTime laterTo = LocalDateTime.of(2024, 9, 1, 0, 0);

        Period max = Period.fromToMax(from);
        assertOverlap(max, Period.pair(later, laterTo));
    }

    @Test
    void should_throw_invalid_period() {
        LocalDateTime from = LocalDateTime.of(2024, 6, 1, 0, 0,  0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        assertThatThrownBy(
                () -> Period.pair(from, to)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_detect_overlap_with_same_from() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0,  0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 1, 0, 0, 0);

        Period a = Period.pair(from, to);

        Period b = Period.pick(from);

        assertThat(b.isOverlap(a)).isTrue();
        assertThat(a.isOverlap(b)).isTrue();
    }

    @Test
    void should_not_detect_overlap() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0,  0);
        LocalDateTime mid = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 1, 0, 0, 0);

        Period a = Period.pair(from, mid);
        Period b = Period.pair(mid, to);

        assertThat(b.isOverlap(a)).isFalse();
        assertThat(a.isOverlap(b)).isFalse();
    }

    @Test
    void should_detect_overlap_with_open_period() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0,  0);
        LocalDateTime mid = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 1, 0, 0, 0);

        Period a = Period.fromToMax(from);
        Period b = Period.pair(from, mid);
        Period c = Period.pair(mid, to);

        assertThat(a.isOpen()).isTrue();
        assertThat(a.to()).isEqualTo(LocalDateTime.MAX);

        assertThat(a.isOverlap(b)).isTrue();
        assertThat(a.isOverlap(c)).isTrue();
    }

    @Test
    void should_split_open_to_max_period() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime targetAt = LocalDateTime.of(2024, 3, 1, 0, 0, 0);

        Period period = Period.fromToMax(from);

        var split = period.splitAt(targetAt);
        var previous = split.previous();
        var next = split.next();

        assertThat(split).isInstanceOf(Period.Split.class);
        assertNoOverlap(previous, next);
        assertThat(previous.to()).isEqualTo(next.from());
        assertThat(next.to()).isEqualTo(LocalDateTime.MAX);
    }

    @Test
    void should_split_both_side_closed_period() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 6, 1, 0, 0, 0);

        LocalDateTime targetAt = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Period period = Period.pair(from, to);

        var split = period.splitAt(targetAt);
        var previous = split.previous();
        var next = split.next();

        assertThat(split).isInstanceOf(Period.Split.class);
        assertNoOverlap(previous, next);
        assertThat(previous.to()).isEqualTo(next.from());
    }

    @Test
    void should_split_throw_invalid_argument() {
        LocalDateTime from = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        LocalDateTime at = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        Period period = Period.pick(from);

        assertThatThrownBy(
                () -> period.splitAt(at)
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(
                () -> period.splitAt(from)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
