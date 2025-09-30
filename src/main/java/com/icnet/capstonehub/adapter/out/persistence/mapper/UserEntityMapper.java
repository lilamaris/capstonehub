package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.UserEntity;
import com.icnet.capstonehub.domain.model.Account;
import com.icnet.capstonehub.domain.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserEntityMapper {
    public static UserEntity toEntity(User domain) {
        UUID id = Optional.ofNullable(domain.id())
                .map(User.Id::value)
                .orElse(null);

        return UserEntity.builder()
                .id(id)
                .name(domain.name())
                .email(domain.email())
                .role(domain.role())
                .build();
    }

    public static User toDomain(UserEntity entity) {
        var id = new User.Id(entity.getId());

        return User.builder()
                .id(id)
                .name(entity.getName())
                .email(entity.getEmail())
                .role(entity.getRole())
                .build();
    }
}
