package com.icnet.capstonehub.domain;

import com.icnet.capstonehub.domain.common.EffectivePeriod;
import lombok.Builder;

@Builder
public record Affiliation(AffiliationId id, Major.MajorId majorId, College.CollegeId collegeId, EffectivePeriod effective) {
    public record AffiliationId(Long value) {}
}
