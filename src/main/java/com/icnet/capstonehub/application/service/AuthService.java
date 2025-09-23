package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.adapter.in.web.request.LoginRequest;
import com.icnet.capstonehub.application.port.in.AuthUseCase;
import com.icnet.capstonehub.application.port.in.result.UserResult;
import com.icnet.capstonehub.application.port.out.AccountPort;
import com.icnet.capstonehub.application.port.out.UserPort;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements AuthUseCase {
    private final UserPort userPort;
    private final AccountPort accountPort;

    @Override
    public Optional<UserResult> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void login(HttpServletRequest request, HttpServletResponse response, LoginRequest body) {
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
    }
}
