package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.DepartmentEntity;
import com.icnet.capstonehub.domain.model.Department;

import java.util.Optional;
import java.util.UUID;

public class DepartmentEntityMapper {
    public static DepartmentEntity toEntity(Department domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(Department.Id::value)
                .orElse(null);

        return DepartmentEntity.builder()
                .id(id)
                .name(domain.name())
                .build();
    }

    public static Department toDomain(DepartmentEntity entity) {
        Department.Id id = new Department.Id(entity.getId());
        return Department.builder()
                .id(id)
                .name(entity.getName())
                .audit(AuditableEntityMapper.toDomain(entity))
                .build();
    }
}
