package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.result.TokenResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import java.util.UUID;

public interface TokenUseCase {
    TokenResult reissue(String token);
    TokenResult issue(UUID userId, String email, String role);
    Claims parseAccessTokenOrThrow(String token) throws JwtException;
}
