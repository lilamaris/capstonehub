package com.icnet.capstonehub.adapter.in.web.request;

import java.time.LocalDate;
import java.util.UUID;

public record AffiliationLineageAmendRequest(
        UUID id,
        UUID collegeId,
        UUID majorId,
        LocalDate validFrom,
        LocalDate validTo,
        String versionDescription
) {}
