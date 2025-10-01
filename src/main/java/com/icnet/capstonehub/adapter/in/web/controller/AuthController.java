package com.icnet.capstonehub.adapter.in.web.controller;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.adapter.in.security.service.JwtService;
import com.icnet.capstonehub.adapter.in.web.mapper.UserResponseMapper;
import com.icnet.capstonehub.adapter.in.web.request.SigninRequest;
import com.icnet.capstonehub.adapter.in.web.request.SignupRequest;
import com.icnet.capstonehub.adapter.in.web.response.UserResponse;
import com.icnet.capstonehub.application.port.in.AccountUseCase;
import com.icnet.capstonehub.application.port.in.command.SignupCredentialCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            UserResponse response = Optional.ofNullable(auth)
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getPrincipal)
                    .map(SecurityUser.class::cast)
                    .map(SecurityUser::getUser)
                    .map(UserResponse::from)
                    .orElseThrow(() -> new BadCredentialsException("No auth found"));
            return ResponseEntity.ok(response);
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            HttpServletRequest request,
            @Valid @RequestBody SigninRequest body
    ) {
        var authToken = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = authenticationManager.authenticate(authToken);
        return ResponseEntity.ok(jwtService.createToken(auth));
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
