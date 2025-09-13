package com.icnet.capstonehub.adapter.in.web.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record AffiliationCreateLineageRequest(
        UUID collegeId,
        UUID majorId,
        LocalDateTime validAt
) {}
