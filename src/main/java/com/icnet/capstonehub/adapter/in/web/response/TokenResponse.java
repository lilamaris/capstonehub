package com.icnet.capstonehub.adapter.in.web.response;

import com.icnet.capstonehub.application.port.in.result.TokenResult;
import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken,
        Integer atMaxAge,
        Integer rtMaxAge
) {
    public static TokenResponse from(TokenResult result) {
        return TokenResponse.builder()
                .accessToken(result.accessToken())
                .refreshToken(result.refreshToken())
                .atMaxAge(result.atMaxAge())
                .rtMaxAge(result.rtMaxAge())
                .build();
    }
}
