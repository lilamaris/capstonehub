package com.icnet.capstonehub.adapter.in.web.request;

import lombok.Builder;

@Builder
public record RefreshTokenRequest(
        String refreshToken
) {
}
