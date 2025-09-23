package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.AffiliationEntity;
import com.icnet.capstonehub.domain.model.Affiliation;

public class AffiliationEntityMapper {
    public static Affiliation toDomain(AffiliationEntity entity) {
        Affiliation.Id id = new Affiliation.Id(entity.getId());
        return Affiliation.builder()
                .id(id)
                .version(VersionEntityMapper.toDomain(entity.getVersion()))
                .lineage(LineageEntityMapper.toDomain(entity.getLineage()))
                .college(CollegeEntityMapper.toDomain(entity.getCollege()))
                .major(MajorEntityMapper.toDomain(entity.getMajor()))
                .build();
    }
}
