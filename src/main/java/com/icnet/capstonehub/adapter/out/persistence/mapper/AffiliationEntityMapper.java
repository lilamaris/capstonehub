package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.AffiliationEntity;
import com.icnet.capstonehub.domain.Affiliation;

import java.util.Optional;
import java.util.UUID;

public class AffiliationEntityMapper {
    public static AffiliationEntity toEntity(Affiliation domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(Affiliation.Id::value)
                .orElse(null);

        return AffiliationEntity.builder()
                .id(id)
                .version(VersionEntityMapper.toEntity(domain.version()))
                .lineage(LineageEntityMapper.toEntity(domain.lineage()))
                .college(CollegeEntityMapper.toEntity(domain.college()))
                .major(MajorEntityMapper.toEntity(domain.major()))
                .build();
    }

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
