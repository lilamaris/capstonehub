package com.icnet.capstonehub.domain;

import com.icnet.capstonehub.domain.common.EffectivePeriod;
import lombok.*;

@Builder
public record College(CollegeId id, String name, EffectivePeriod effective) {
    public record CollegeId(Long value) {}
}
