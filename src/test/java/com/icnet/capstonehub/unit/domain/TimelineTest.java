package com.icnet.capstonehub.unit.domain;

import com.icnet.capstonehub.domain.model.Timeline;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TimelineTest {
    @Test
    void should_create_new_lineage() {
        var validFrom = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        Timeline l1 = Timeline.initial(validFrom);

        assertThat(l1).isInstanceOf(Timeline.class);
        assertThat(l1.id()).isNotNull();
        assertThat(l1.sharedId()).isNotNull();
        assertThat(l1.isHead()).isTrue();
    }

    @Test
    void should_create_next_lineage() {
        var validFrom = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        Timeline l1 = Timeline.initial(validFrom);

        var validTo = LocalDateTime.of(2024, 7, 1, 0, 0, 0);
        Timeline.Transition transition = l1.migrate(validTo);

        var previous = transition.previous();
        var next = transition.next();

        assertThat(next).isInstanceOf(Timeline.class);
        assertThat(previous.id()).isNotEqualTo(next.id());
        assertThat(previous.sharedId()).isEqualTo(next.sharedId());
        assertThat(previous.isHead()).isFalse();

        assertThat(next.isHead()).isTrue();

        assertThat(previous.validPeriod().isOverlap(next.validPeriod())).isFalse();
        assertThat(previous.validPeriod().to()).isEqualTo(next.validPeriod().from());
    }
}
