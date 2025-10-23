package com.icnet.capstonehub.adapter.in.security.config;

import com.icnet.capstonehub.adapter.in.security.service.SecurityUserDetailsService;
import com.icnet.capstonehub.application.port.in.TokenUseCase;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final SecurityUserDetailsService userDetailsService;
    private final TokenUseCase tokenUseCase;

    @Override
    public void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/api/v1/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("JWT Filter Chain");

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            log.info("JWT Filter Chain: Already authenticated");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("JWT Filter Chain: Auth header not found");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);

        final Claims claims;
        try {
            claims = tokenUseCase.parseAccessTokenOrThrow(token);
        } catch (ExpiredJwtException e) {
            request.setAttribute("jwtError", "expired");
            throw new CredentialsExpiredException("Expired Token", e);
        } catch (SignatureException e) {
            request.setAttribute("jwtError", "invalid");
            throw new BadCredentialsException("Invalid Token", e);
        } catch (JwtException e) {
            request.setAttribute("jwtError", "invalid");
            throw new InsufficientAuthenticationException("Jwt Invalid", e);
        }

        var email = claims.getSubject();

        var userDetail = userDetailsService.loadUserByUsername(email);
        var auth = new UsernamePasswordAuthenticationToken(
                userDetail, null, userDetail.getAuthorities()
        );

        log.info("JWT Filter Chain: Set user auth {},{}",
                auth, auth.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
