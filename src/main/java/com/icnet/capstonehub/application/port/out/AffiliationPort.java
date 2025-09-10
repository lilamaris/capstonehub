package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AffiliationPort {
    Optional<Affiliation> getSnapshotOfRecord(Lineage.SharedId lineageSharedId, Version.SharedId versionSharedId, LocalDate txAt);
    Optional<Affiliation> getSnapshotOfRecord(Lineage.SharedId lineageSharedId, LocalDate validAt, LocalDate txAt);
    List<Affiliation> getLineageOfSnapshot(Lineage.SharedId lineageSharedId, LocalDate txAt);
    List<Affiliation> getVersionOfRecord(Lineage.SharedId lineageSharedId, Version.SharedId versionSharedId);
    List<Affiliation> getVersionOfRecord(Lineage.SharedId lineageSharedId, LocalDate validAt);
    Affiliation save(Affiliation domain);
}
