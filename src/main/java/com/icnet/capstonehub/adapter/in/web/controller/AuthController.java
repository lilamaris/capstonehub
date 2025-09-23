package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.adapter.in.web.request.LoginRequest;
import com.icnet.capstonehub.application.port.in.AuthUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            HttpServletRequest request,
            @Valid @RequestBody LoginRequest body
    ) {
        log.info("Login request");
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(body.email(), body.password())
    );
        SecurityContextHolder.getContext().setAuthentication(auth);
        HttpSession session = request.getSession(true);
        SecurityUser user = (SecurityUser) auth.getPrincipal();

        return ResponseEntity.ok(Map.of(
                "sessionId", session.getId(),
                "user", Map.of(
                        "email", user.getUsername(),
                        "role", user.getAuthorities()
                )
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("OK");
    }
}
