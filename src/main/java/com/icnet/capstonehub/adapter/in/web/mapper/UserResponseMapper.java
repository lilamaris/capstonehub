package com.icnet.capstonehub.adapter.in.web.mapper;

import com.icnet.capstonehub.adapter.in.web.response.UserResponse;
import com.icnet.capstonehub.application.port.in.result.UserResult;

public class UserResponseMapper {
    public static UserResponse toResponse(UserResult result) {
        return UserResponse.builder()
                .id(result.id())
                .name(result.name())
                .email(result.email())
                .role(result.role())
                .build();
    }
}
