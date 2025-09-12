package com.icnet.capstonehub.unit.domain;

import com.icnet.capstonehub.domain.model.Period;
import com.icnet.capstonehub.domain.model.Version;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VersionTest {
    @Test
    void should_create_new_version() {
        var txFrom = LocalDate.of(2024, 1, 1);
        Version v1 = Version.initial(txFrom);

        assertThat(v1).isInstanceOf(Version.class);
        assertThat(v1.sharedId()).isNotNull();
        assertThat(v1.versionNo()).isEqualTo(1);
        assertThat(v1.versionDescription()).isEqualTo("Initial version");
        assertThat(v1.isHead()).isTrue();
    }

    @Test
    void should_increment_version_number() {
        var txFrom = LocalDate.of(2024, 1, 1);
        Version v1 = Version.initial(txFrom);

        var nextFrom = LocalDate.of(2024, 2, 1);
        Version v2 = v1.next(nextFrom, "new version");

        assertThat(v2).isInstanceOf(Version.class);
        assertThat(v2.versionNo()).isEqualTo(v1.versionNo() + 1);
        assertThat(v2.sharedId()).isEqualTo(v1.sharedId());
        assertThat(v2.txPeriod().from()).isEqualTo(nextFrom);
    }

    @Test
    void should_close_previous_version_and_not_overlap() {
        var txFrom = LocalDate.of(2024, 1, 1);
        Version v1 = Version.initial(txFrom);

        var nextFrom = LocalDate.of(2024, 2, 1);
        Version v2 = v1.next(nextFrom, "new version");

        v1 = v1.close(nextFrom);

        assertThat(v1.isHead()).isFalse();
        assertThat(v1.txPeriod().to()).isEqualTo(nextFrom);
        assertThat(v2.txPeriod().from()).isEqualTo(nextFrom);
        assertThat(Period.isOverlap(v1.txPeriod(), v2.txPeriod())).isFalse();
    }

    @Test
    void should_throw_except_try_close_already_closed() {
        var txFrom = LocalDate.of(2024, 1, 1);
        Version v1 = Version.initial(txFrom);

        var nextFrom = LocalDate.now();
        Version closedV1 = v1.close(nextFrom);

        assertThat(closedV1.isHead()).isFalse();

        assertThatThrownBy(
                () -> closedV1.close(nextFrom.plusDays(1))
        ).isInstanceOf(IllegalStateException.class);
    }
}
