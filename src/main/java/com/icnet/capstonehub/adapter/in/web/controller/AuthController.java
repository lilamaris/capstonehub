package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.adapter.in.web.mapper.UserResponseMapper;
import com.icnet.capstonehub.adapter.in.web.request.SigninRequest;
import com.icnet.capstonehub.adapter.in.web.request.SignupRequest;
import com.icnet.capstonehub.adapter.in.web.response.UserResponse;
import com.icnet.capstonehub.application.port.in.AccountUseCase;
import com.icnet.capstonehub.application.port.in.command.SignupCredentialCommand;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final AccountUseCase accountUseCase;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            HttpServletRequest request,
            @Valid @RequestBody SigninRequest body
    ) {
        log.info("Signin request");
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(body.email(), body.password());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        return ResponseEntity.ok(authenticationResponse);
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

        return ResponseEntity.ok(UserResponseMapper.toResponse(result));
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signout() {
        return ResponseEntity.ok("OK");
    }
}
