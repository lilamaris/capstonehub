package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SigninResponse(
        String accessToken,
        UUID refreshToken
) {
}
