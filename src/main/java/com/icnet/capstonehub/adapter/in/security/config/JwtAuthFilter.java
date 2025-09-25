package com.icnet.capstonehub.adapter.in.security.config;

import com.icnet.capstonehub.adapter.in.security.service.JwtService;
import com.icnet.capstonehub.adapter.in.security.service.SecurityUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

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

        log.info("JWT Auth filter: start validate token. {}", token);

        if (!jwtService.validateToken(token)) {
            log.info("JWT Auth filter: jwt invalid");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("JWT Auth filter: start create jwt authentication for request");
        String username = jwtService.getUsername(token);
        var userDetail = userDetailsService.loadUserByUsername(username);
        var authToken = new UsernamePasswordAuthenticationToken(
                userDetail, null, userDetail.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        log.info("JWT Auth filter: end create jwt authentication for request");
        filterChain.doFilter(request, response);
    }
}
