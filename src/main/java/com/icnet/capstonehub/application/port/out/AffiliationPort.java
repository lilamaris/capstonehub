package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.Affiliation;
import com.icnet.capstonehub.domain.model.Lineage;
import com.icnet.capstonehub.domain.model.Version;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AffiliationPort {
    Optional<Affiliation> getSnapshotOfRecord(Lineage.SharedId lineageSharedId, Version.SharedId versionSharedId, LocalDateTime txAt);
    Optional<Affiliation> getSnapshotOfRecord(Lineage.SharedId lineageSharedId, LocalDateTime validAt, LocalDateTime txAt);
    List<Affiliation> getLineageOfSnapshot(Lineage.SharedId lineageSharedId, LocalDateTime txAt);
    List<Affiliation> getVersionOfRecord(Lineage.SharedId lineageSharedId, Version.SharedId versionSharedId);
    List<Affiliation> getVersionOfRecord(Lineage.SharedId lineageSharedId, LocalDateTime validAt);
    Affiliation save(Affiliation domain);
}
