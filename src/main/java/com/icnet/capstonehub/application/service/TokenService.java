package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.TokenUseCase;
import com.icnet.capstonehub.application.port.out.RefreshTokenPort;
import com.icnet.capstonehub.application.port.out.UserPort;
import com.icnet.capstonehub.domain.model.RefreshToken;
import com.icnet.capstonehub.domain.model.User;
import io.jsonwebtoken.*;
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

    private Claims getClaims(String token) {
        return getParser(key).parseSignedClaims(token).getPayload();
    }

    private  <T> T resolveClaim(String token, Function<Claims, T> resolver) {
        final var claims = getClaims(token);
        return resolver.apply(claims);
    }

    @Override
    public String getUserRole(String token) {
        final var claims = getClaims(token);
        return claims.get("role", String.class);
    }

    @Override
    public String getUserEmail(String token) {
        return resolveClaim(token, Claims::getSubject);
    }

    @Override
    public UUID issueRefreshToken(UUID userId) {
        var token = UUID.randomUUID();
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

    @Override
    public String issueAccessToken(String email, String role) {
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
    public String reissue(UUID token) {
        var now = Instant.now();
        var refreshToken = refreshTokenPort.getByToken(token).orElseThrow(IllegalStateException::new);

        if (refreshToken.expiredAt().isBefore(now)) {
            refreshTokenPort.delete(token);
            throw new IllegalStateException();
        }

        var user = userPort.getById(refreshToken.userId()).orElseThrow(IllegalStateException::new);
        return issueAccessToken(user.email(), user.role().name());
    }

    @Override
    public Boolean validateAccessToken(String token) {
        var parser = getParser(key);
        try {
            parser.parse(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid accessToken: {}, accessToken: {}", e.getMessage(), token);
        } catch (ExpiredJwtException e) {
            log.error("Token is expired: {}, accessToken: {}", e.getMessage(), token);
        } catch (UnsupportedJwtException e) {
            log.error("Token is unsupport: {}, accessToken: {}", e.getMessage(), token);
        } catch (IllegalArgumentException e) {
            log.error("Invalid accessToken claim: {}, accessToken: {}", e.getMessage(), token);
        }
        return false;
    }
}
