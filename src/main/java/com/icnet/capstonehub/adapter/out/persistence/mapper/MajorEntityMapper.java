package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.domain.Major;

public class MajorEntityMapper {
    public static MajorEntity toEntity(Major domain) {
        if (domain == null) return null;
        return MajorEntity.builder()
                .name(domain.name())
                .build();
    }

    public static Major toDomain(MajorEntity entity) {
        if (entity == null) return null;
        return Major.builder()
                .id(new Major.Id(entity.getId()))
                .name(entity.getName())
                .build();
    }
}
