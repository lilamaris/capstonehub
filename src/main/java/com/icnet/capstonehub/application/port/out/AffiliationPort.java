package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AffiliationPort {
    Optional<Affiliation> get(Affiliation.Id id);
    Optional<Affiliation> getLineageHead(Lineage.LineageId lineageId);
    List<Affiliation> getLineageSnapshotAtTx(Lineage.LineageId lineageId, LocalDate txAt);
    List<Affiliation> getLineageSnapshotAtTxOnDate(Lineage.LineageId lineageId, LocalDate txAt, LocalDate on);
    Affiliation save(Affiliation domain);
}
