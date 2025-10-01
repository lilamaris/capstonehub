package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.adapter.in.web.request.RefreshTokenRequest;
import com.icnet.capstonehub.adapter.in.web.request.SigninRequest;
import com.icnet.capstonehub.adapter.in.web.request.SignupRequest;
import com.icnet.capstonehub.adapter.in.web.response.RefreshTokenResponse;
import com.icnet.capstonehub.adapter.in.web.response.SigninResponse;
import com.icnet.capstonehub.adapter.in.web.response.UserResponse;
import com.icnet.capstonehub.application.port.in.AccountUseCase;
import com.icnet.capstonehub.application.port.in.TokenUseCase;
import com.icnet.capstonehub.application.port.in.UserUseCase;
import com.icnet.capstonehub.application.port.in.command.SignupCredentialCommand;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserUseCase userUseCase;
    private final TokenUseCase tokenUseCase;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("/me auth: {}", auth);

        return Optional.ofNullable(auth)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(String.class::cast)
                .map(email -> {
                    var response = UserResponse.from(userUseCase.getByEmail(email));
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(
            @RequestBody RefreshTokenRequest request
    ) {
        var token = request.token();

        var response = RefreshTokenResponse.builder()
                .accessToken(tokenUseCase.reissue(token))
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(
            HttpServletRequest request,
            @Valid @RequestBody SigninRequest body
    ) {
        var authToken = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = authenticationManager.authenticate(authToken);
        var user = ((SecurityUser) auth.getPrincipal()).getUser();
        var accessToken = tokenUseCase.issueAccessToken(user.email(), user.role());
        var refreshToken = tokenUseCase.issueRefreshToken(user.id());
        var response = SigninResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

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
