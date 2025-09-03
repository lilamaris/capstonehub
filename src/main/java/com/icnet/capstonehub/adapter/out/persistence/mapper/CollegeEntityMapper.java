package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.domain.College;
import com.icnet.capstonehub.domain.common.EffectivePeriod;
import org.springframework.stereotype.Component;

@Component
public class CollegeEntityMapper {
    public CollegeEntity toEntity(College domain) {
        if (domain == null) return null;
        return CollegeEntity.builder()
                .name(domain.name())
                .effectiveStartDate(domain.effective().start())
                .effectiveEndDate(domain.effective().end())
                .build();
    }

    public College toDomain(CollegeEntity entity) {
        if (entity == null) return null;
        return College.builder()
                .id(new College.CollegeId(entity.getId()))
                .name(entity.getName())
                .effective(new EffectivePeriod(entity.getEffectiveStartDate(), entity.getEffectiveEndDate()))
                .build();
    }
}
