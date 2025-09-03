package com.icnet.capstonehub.domain;

import com.icnet.capstonehub.domain.common.EffectivePeriod;
import lombok.*;

@Builder
public record Major(MajorId id, String name, EffectivePeriod effective) {
    public record MajorId(Long value) {
    }
}
