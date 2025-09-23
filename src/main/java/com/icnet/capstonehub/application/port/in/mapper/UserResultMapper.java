package com.icnet.capstonehub.application.port.in.mapper;

import com.icnet.capstonehub.application.port.in.result.UserResult;
import com.icnet.capstonehub.domain.model.User;

public class UserResultMapper {
    public static UserResult toResult(User domain) {
        return UserResult.builder()
                .id(domain.id().value())
                .name(domain.name())
                .email(domain.email())
                .role(domain.role().name())
                .build();
    }
}
