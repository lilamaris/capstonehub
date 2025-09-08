package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.domain.College;

public class CollegeEntityMapper {
    public static CollegeEntity toEntity(College domain) {
        if (domain == null) return null;
        return CollegeEntity.builder()
                .name(domain.name())
                .build();
    }

    public static College toDomain(CollegeEntity entity) {
        if (entity == null) return null;
        return College.builder()
                .id(new College.Id(entity.getId()))
                .name(entity.getName())
                .build();
    }
}
