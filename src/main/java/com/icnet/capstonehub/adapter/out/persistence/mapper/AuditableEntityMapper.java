package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.AuditableEntity;
import com.icnet.capstonehub.domain.model.Audit;
import com.icnet.capstonehub.domain.model.User;

public class AuditableEntityMapper {
        public static Audit toDomain(AuditableEntity entity) {
            User.Id createdBy = new User.Id(entity.getCreatedBy());
            User.Id updatedBy = new User.Id(entity.getUpdatedBy());
            return Audit.builder()
                    .createdBy(createdBy)
                    .createdAt(entity.getCreatedAt())
                    .updatedBy(updatedBy)
                    .updatedAt(entity.getUpdatedAt())
                    .build();
        }
}
