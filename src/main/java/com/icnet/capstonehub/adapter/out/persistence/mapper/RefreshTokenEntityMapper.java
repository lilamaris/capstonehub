package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.RefreshTokenEntity;
import com.icnet.capstonehub.domain.model.RefreshToken;
import com.icnet.capstonehub.domain.model.User;

public class RefreshTokenEntityMapper {
    public static RefreshToken toDomain(RefreshTokenEntity entity) {
        var id = new RefreshToken.Id(entity.getId());
        var userId = new User.Id(entity.getUser().getId());
        return RefreshToken.builder()
                .id(id)
                .userId(userId)
                .token(entity.getToken())
                .expiredAt(entity.getExpiredAt())
                .build();
    }
}
