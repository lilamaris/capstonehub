package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.LectureEntity;
import com.icnet.capstonehub.domain.Lecture;
import com.icnet.capstonehub.domain.common.EffectivePeriod;
import org.springframework.stereotype.Component;

@Component
public class LectureEntityMapper {
    public LectureEntity toEntity(Lecture domain) {
        if (domain == null) return null;
        return LectureEntity.builder()
                .name(domain.name())
                .effectiveStartDate(domain.effective().start())
                .effectiveEndDate(domain.effective().end())
                .build();
    }

    public Lecture toDomain(LectureEntity entity) {
        if (entity == null) return null;
        return Lecture.builder()
                .id(new Lecture.LectureId(entity.getId()))
                .name(entity.getName())
                .effective(new EffectivePeriod(entity.getEffectiveStartDate(), entity.getEffectiveEndDate()))
                .build();
    }
}
