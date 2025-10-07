package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.adapter.in.web.request.RefreshTokenRequest;
import com.icnet.capstonehub.adapter.in.web.request.SigninRequest;
import com.icnet.capstonehub.adapter.in.web.request.SignupRequest;
import com.icnet.capstonehub.adapter.in.web.response.TokenResponse;
import com.icnet.capstonehub.adapter.in.web.response.UserResponse;
import com.icnet.capstonehub.application.port.in.AccountUseCase;
import com.icnet.capstonehub.application.port.in.TokenUseCase;
import com.icnet.capstonehub.application.port.in.UserUseCase;
import com.icnet.capstonehub.application.port.in.command.SignupCredentialCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AccountUseCase accountUseCase;
    private final TokenUseCase tokenUseCase;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            @RequestBody RefreshTokenRequest request
    ) {
        var token = request.refreshToken();
        var response = TokenResponse.from(tokenUseCase.reissue(token));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @Valid @RequestBody SigninRequest body
    ) {
        var authToken = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = authenticationManager.authenticate(authToken);
        var user = ((SecurityUser) auth.getPrincipal()).getUser();
        var response = TokenResponse.from(tokenUseCase.issue(user.id(), user.email(), user.role()));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(
            @Valid @RequestBody SignupRequest body
    ) {
        var command = SignupCredentialCommand.builder()
                .name(body.name())
                .email(body.email())
                .passwordHash(passwordEncoder.encode(body.password()))
                .build();
        var result = accountUseCase.signupCredential(command);

        return ResponseEntity.ok(UserResponse.from(result));
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signout() {
        return ResponseEntity.ok("OK");
    }
}
