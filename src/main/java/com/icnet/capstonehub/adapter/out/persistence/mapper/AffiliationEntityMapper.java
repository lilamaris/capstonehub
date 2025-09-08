package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.AffiliationEntity;
import com.icnet.capstonehub.domain.Affiliation;

public class AffiliationEntityMapper {
    public static AffiliationEntity toEntity(Affiliation domain) {
        return AffiliationEntity.builder()
                .version(VersionEntityMapper.toEntity(domain.version()))
                .college(CollegeEntityMapper.toEntity(domain.college()))
                .major(MajorEntityMapper.toEntity(domain.major()))
                .build();
    }

    public static Affiliation toDomain(AffiliationEntity entity) {
        return Affiliation.builder()
                .id(new Affiliation.Id(entity.getId()))
                .version(VersionEntityMapper.toDomain(entity.getVersion()))
                .college(CollegeEntityMapper.toDomain(entity.getCollege()))
                .major(MajorEntityMapper.toDomain(entity.getMajor()))
                .build();
    }
}
