package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.join.AffiliationEntity;
import com.icnet.capstonehub.domain.Affiliation;
import com.icnet.capstonehub.domain.College;
import com.icnet.capstonehub.domain.Major;
import com.icnet.capstonehub.domain.common.EffectivePeriod;
import org.springframework.stereotype.Component;

@Component
public class AffiliationEntityMapper {
    public AffiliationEntity toEntity(CollegeEntity collegeEntity, MajorEntity majorEntity, EffectivePeriod period) {
        return AffiliationEntity.builder()
                .college(collegeEntity)
                .major(majorEntity)
                .effectiveStartDate(period.start())
                .effectiveEndDate(period.end())
                .build();
    }

    public Affiliation toDomain(AffiliationEntity entity, College college, Major major) {
        if (entity == null) return null;
        return Affiliation.builder()
                .id(new Affiliation.AffiliationId(entity.getId()))
                .college(college)
                .major(major)
                .effective(new EffectivePeriod(entity.getEffectiveStartDate(), entity.getEffectiveEndDate()))
                .build();
    }
}
