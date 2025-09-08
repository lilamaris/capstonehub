package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AffiliationPort {
    List<Affiliation> getLineage(Version.LineageId lineageId);
    Optional<Affiliation> getCurrent(Version.LineageId lineageId);
    Optional<Affiliation> getCurrent(Version.LineageId lineageId, LocalDate current);
    Affiliation save(Affiliation domain);
}
