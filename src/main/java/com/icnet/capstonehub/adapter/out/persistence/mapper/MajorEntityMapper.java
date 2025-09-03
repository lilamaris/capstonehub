package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.domain.Major;
import com.icnet.capstonehub.domain.common.EffectivePeriod;
import org.springframework.stereotype.Component;

@Component
public class MajorEntityMapper {
    public MajorEntity toEntity(Major domain) {
        if (domain == null) return null;
        return MajorEntity.builder()
                .name(domain.name())
                .effectiveStartDate(domain.effective().start())
                .effectiveEndDate(domain.effective().end())
                .build();
    }

    public Major toDomain(MajorEntity entity) {
        if (entity == null) return null;
        return Major.builder()
                .id(new Major.MajorId(entity.getId()))
                .name(entity.getName())
                .effective(new EffectivePeriod(entity.getEffectiveStartDate(), entity.getEffectiveEndDate()))
                .build();
    }
}
