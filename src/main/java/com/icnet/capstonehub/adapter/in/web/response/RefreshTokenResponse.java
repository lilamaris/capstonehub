package com.icnet.capstonehub.adapter.in.web.response;

import lombok.Builder;

@Builder
public record RefreshTokenResponse(
        String accessToken
) {}
