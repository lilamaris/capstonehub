package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.FacultyEntity;
import com.icnet.capstonehub.domain.model.Faculty;

import java.util.Optional;
import java.util.UUID;

public class FacultyEntityMapper {
    public static FacultyEntity toEntity(Faculty domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(Faculty.Id::value)
                .orElse(null);

        return FacultyEntity.builder()
                .id(id)
                .name(domain.name())
                .build();
    }

    public static Faculty toDomain(FacultyEntity entity) {
        Faculty.Id id = new Faculty.Id(entity.getId());
        return Faculty.builder()
                .id(id)
                .name(entity.getName())
                .audit(AuditableEntityMapper.toDomain(entity))
                .build();
    }
}
