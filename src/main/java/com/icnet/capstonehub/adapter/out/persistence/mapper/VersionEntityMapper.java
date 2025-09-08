package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.VersionEntity;
import com.icnet.capstonehub.domain.Version;

public class VersionEntityMapper {
    public static VersionEntity toEntity(Version domain) {
        return VersionEntity.builder()
                .lineageId(domain.lineageId().value())
                .lineageScope(domain.lineageScope())
                .versionNo(domain.versionNo())
                .validFrom(domain.validFrom())
                .validTo(domain.validTo())
                .versionDescription(domain.versionDescription())
                .build();
    }

    public static Version toDomain(VersionEntity entity) {
        return Version.builder()
                .id(new Version.Id(entity.getId()))
                .lineageId(new Version.LineageId(entity.getLineageId()))
                .lineageScope(entity.getLineageScope())
                .versionNo(entity.getVersionNo())
                .validFrom(entity.getValidFrom())
                .validTo(entity.getValidTo())
                .versionDescription(entity.getVersionDescription())
                .build();
    }
}
