package com.icnet.capstonehub.adapter.in.security.service;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtService {
    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String createToken(Authentication authentication) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        var now = new Date();
        var expiration = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    private JwtParser getParser(SecretKey key) {
        return Jwts.parser().verifyWith(key).build();
    }

    private Claims getClaims(String token) {
        return getParser(key).parseSignedClaims(token).getPayload();
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public <T> T getClaim(String token, Function<Claims, T> resolver) {
        final var claims = getClaims(token);
        return resolver.apply(claims);
    }

    public Boolean validateToken(String token) {
        var parser = getParser(key);
        try {
            parser.parse(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid token: {}, token: {}", e.getMessage(), token);
        } catch (ExpiredJwtException e) {
            log.error("Token is expired: {}, token: {}", e.getMessage(), token);
        } catch (UnsupportedJwtException e) {
            log.error("Token is unsupport: {}, token: {}", e.getMessage(), token);
        } catch (IllegalArgumentException e) {
            log.error("Invalid token claim: {}, token: {}", e.getMessage(), token);
        }
        return false;
    }
}
