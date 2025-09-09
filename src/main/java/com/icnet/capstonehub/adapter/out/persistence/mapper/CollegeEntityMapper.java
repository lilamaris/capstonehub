package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.CollegeEntity;
import com.icnet.capstonehub.domain.College;

import java.util.Optional;
import java.util.UUID;

public class CollegeEntityMapper {
    public static CollegeEntity toEntity(College domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(UUID.class::cast)
                .orElse(null);

        return CollegeEntity.builder()
                .id(id)
                .name(domain.name())
                .build();
    }

    public static College toDomain(CollegeEntity entity) {
        College.Id id = new College.Id(entity.getId());
        return College.builder()
                .id(id)
                .name(entity.getName())
                .build();
    }
}
