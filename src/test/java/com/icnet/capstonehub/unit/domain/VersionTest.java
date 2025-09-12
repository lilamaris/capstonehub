package com.icnet.capstonehub.unit.domain;

import com.icnet.capstonehub.domain.model.Version;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VersionTest {
    @Test
    void should_create_new_version() {
        var txFrom = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        Version v1 = Version.initial(txFrom);

        assertThat(v1).isInstanceOf(Version.class);
        assertThat(v1.id()).isNotNull();
        assertThat(v1.sharedId()).isNotNull();
        assertThat(v1.versionNo()).isEqualTo(1);
        assertThat(v1.versionDescription()).isEqualTo("Initial version");
        assertThat(v1.isHead()).isTrue();
    }

    @Test
    void should_create_next_version() {
        var txFrom = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        Version v1 = Version.initial(txFrom);

        var txTo = LocalDateTime.of(2024, 3, 1, 2, 42, 1);
        var versionDescription = "Next Version";
        var transition = v1.migrate(txTo, versionDescription);

        var previous = transition.previous();
        var next = transition.next();

        assertThat(next).isInstanceOf(Version.class);
        assertThat(previous.id()).isNotEqualTo(next.id());
        assertThat(previous.sharedId()).isEqualTo(next.sharedId());
        assertThat(previous.isHead()).isFalse();

        assertThat(next.versionNo()).isEqualTo(previous.versionNo() + 1);
        assertThat(next.versionDescription()).isEqualTo(versionDescription);
        assertThat(next.isHead()).isTrue();

        assertThat(previous.txPeriod().isOverlap(next.txPeriod())).isFalse();
        assertThat(previous.txPeriod().to()).isEqualTo(next.txPeriod().from());
    }

    @Test
    void should_throw_invalid_argument() {
        var txFrom = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        Version v1 = Version.initial(txFrom);

        var txTo = LocalDateTime.of(2024, 3, 1, 2, 42, 1);
        var versionDescription = "Next Version";
        var transition = v1.migrate(txTo, versionDescription);

        var previous = transition.previous();
        var next = transition.next();

        var anotherTxTo = LocalDateTime.of(2024, 2, 1, 2, 42, 1);

        assertThat(previous.isHead()).isFalse();
        assertThatThrownBy(
                () -> previous.migrate(anotherTxTo, "This is invalid version migration")
        ).isInstanceOf(IllegalStateException.class);
    }

}
