package com.icnet.capstonehub.adapter.in.security.config;

import com.icnet.capstonehub.application.port.in.TokenUseCase;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final TokenUseCase tokenUseCase;

    @Override
    public void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        log.info("JWT Auth filter start");

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            log.info("JWT Auth filter: authentication context not exists");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("JWT Auth filter: auth header invalid");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        log.info("JWT Auth filter: start validateRefreshToken accessToken. {}", token);

        if (!tokenUseCase.validateAccessToken(token)) {
            log.info("JWT Auth filter: jwt invalid");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("JWT Auth filter: start create jwt authentication for request");
        String email = tokenUseCase.getUserEmail(token);
        Collection<? extends GrantedAuthority> authorities = Stream.of(tokenUseCase.getUserRole(token)).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        var auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info("JWT Auth filter: end create jwt authentication for request");
        filterChain.doFilter(request, response);
    }
}
