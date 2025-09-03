package com.icnet.capstonehub.domain;

import com.icnet.capstonehub.domain.common.EffectivePeriod;
import lombok.*;

@Builder
public record Lecture(LectureId id, String name, EffectivePeriod effective) {
    public record LectureId(Long value) {
    }
}
