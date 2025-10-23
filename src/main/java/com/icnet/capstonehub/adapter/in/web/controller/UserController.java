package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.adapter.in.web.response.UserResponse;
import com.icnet.capstonehub.application.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserUseCase userUseCase;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("/me auth: {}", auth);

        return Optional.ofNullable(auth)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SecurityUser.class::cast)
                .map(user -> {
                    var response = UserResponse.from(userUseCase.getByEmail(user.getUsername()));
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.badRequest().build());
    }
}
