package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.result.AffiliationResult;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AffiliationUseCase {
    List<AffiliationResult> getAffiliationLineage(UUID lineageId);
    AffiliationResult initialAffiliationLineage(UUID collegeId, UUID majorId, LocalDate validFrom, LocalDate validTo, String versionDescription);
    AffiliationResult appendAffiliationLineage(UUID lineageId, UUID collegeId, UUID majorId, LocalDate validFrom, LocalDate validTo, String versionDescription);
    AffiliationResult amendAffiliationLineage(UUID lineageId, UUID affiliationId, UUID collegeId, UUID majorId, LocalDate validFrom, LocalDate validTo, String versionDescription);
}
