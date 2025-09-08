package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.result.AffiliationResult;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AffiliationUseCase {
    List<AffiliationResult> getAffiliationLineage(UUID lineageId);
    AffiliationResult createAffiliationLineage(UUID collegeId, UUID majorId, LocalDate startDate, LocalDate endDate, String versionDescription);
    AffiliationResult successionMajorLineage(UUID lineageId, UUID majorId, LocalDate startDate, LocalDate endDate, String versionDescription);
    AffiliationResult successionCollegeLineage(UUID lineageId, UUID collegeId, LocalDate startDate, LocalDate endDate, String versionDescription);
}
