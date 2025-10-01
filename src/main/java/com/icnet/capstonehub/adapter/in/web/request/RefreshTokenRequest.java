package com.icnet.capstonehub.adapter.in.web.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RefreshTokenRequest(
        UUID token
) {
}
