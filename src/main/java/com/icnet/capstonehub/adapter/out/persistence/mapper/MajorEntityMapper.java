package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import com.icnet.capstonehub.domain.model.Major;

import java.util.Optional;
import java.util.UUID;

public class MajorEntityMapper {
    public static MajorEntity toEntity(Major domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(Major.Id::value)
                .orElse(null);

        return MajorEntity.builder()
                .id(id)
                .name(domain.name())
                .build();
    }

    public static Major toDomain(MajorEntity entity) {
        Major.Id id = new Major.Id(entity.getId());
        return Major.builder()
                .id(id)
                .name(entity.getName())
                .build();
    }
}
