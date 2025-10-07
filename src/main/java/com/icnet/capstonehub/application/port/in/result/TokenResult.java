package com.icnet.capstonehub.application.port.in.result;

import lombok.Builder;

@Builder
public record TokenResult (
        String accessToken,
        String refreshToken,
        Integer atMaxAge,
        Integer rtMaxAge
) {}
