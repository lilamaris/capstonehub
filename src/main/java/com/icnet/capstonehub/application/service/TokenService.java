package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.TokenUseCase;
import com.icnet.capstonehub.application.port.in.result.TokenResult;
import com.icnet.capstonehub.application.port.out.RefreshTokenPort;
import com.icnet.capstonehub.application.port.out.UserPort;
import com.icnet.capstonehub.domain.model.RefreshToken;
import com.icnet.capstonehub.domain.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TokenService implements TokenUseCase {
    private final UserPort userPort;
    private final RefreshTokenPort refreshTokenPort;

    @Value("${app.RTExpirationMs}")
    private int RTExpirationMs;

    @Value("${app.ATExpirationMs}")
    private int ATExpirationMs;

    private final SecretKey key = Jwts.SIG.HS256.key().build();

    private JwtParser getParser(SecretKey key) {
        return Jwts.parser().verifyWith(key).build();
    }

    private String issueRefreshToken(UUID userId) {
        var token = UUID.randomUUID().toString();
        var uId = new User.Id(userId);

        var existsId = refreshTokenPort.getByUserId(uId).map(RefreshToken::id).orElse(null);

        var refreshToken = RefreshToken.builder()
                .id(existsId)
                .userId(uId)
                .token(token)
                .expiredAt(Instant.now().plusMillis(RTExpirationMs))
                .build();

        var saved = refreshTokenPort.save(refreshToken);

        return saved.token();
    }

    private String issueAccessToken(String email, String role) {
        var now = new Date();
        var expiration = new Date(now.getTime() + ATExpirationMs);

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    @Override
    public Claims parseAccessTokenOrThrow(String token) throws JwtException {
        return getParser(key).parseSignedClaims(token).getPayload();
    }

    @Override
    public TokenResult issue(UUID userId, String email, String role) {
        var at = issueAccessToken(email, role);
        var rt = issueRefreshToken(userId);

        return TokenResult.builder()
                .accessToken(at)
                .refreshToken(rt)
                .atMaxAge(ATExpirationMs / 1000)
                .rtMaxAge(RTExpirationMs / 1000)
                .build();
    }

    @Override
    public TokenResult reissue(String token) {
        var now = Instant.now();
        var refreshToken = refreshTokenPort.getByToken(token).orElseThrow(IllegalStateException::new);

        if (refreshToken.expiredAt().isBefore(now)) {
            refreshTokenPort.delete(token);
            throw new IllegalStateException();
        }
        var user = userPort.getById(refreshToken.userId()).orElseThrow(IllegalStateException::new);

        return issue(user.id().value(), user.email(), user.role().name());
    }
}
