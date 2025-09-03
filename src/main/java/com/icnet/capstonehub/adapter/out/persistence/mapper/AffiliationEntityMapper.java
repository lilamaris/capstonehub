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
    public AffiliationEntity toEntity(Affiliation domain, CollegeEntity collegeEntity, MajorEntity majorEntity) {
        if (domain == null) return null;
        return AffiliationEntity.builder()
                .college(collegeEntity)
                .major(majorEntity)
                .effectiveStartDate(domain.effective().start())
                .effectiveEndDate(domain.effective().end())
                .build();
    }

    public Affiliation toDomain(AffiliationEntity entity) {
        if (entity == null) return null;
        return Affiliation.builder()
                .id(new Affiliation.AffiliationId(entity.getId()))
                .collegeId(new College.CollegeId(entity.getCollege().getId()))
                .majorId(new Major.MajorId(entity.getMajor().getId()))
                .effective(new EffectivePeriod(entity.getEffectiveStartDate(), entity.getEffectiveEndDate()))
                .build();
    }
}
