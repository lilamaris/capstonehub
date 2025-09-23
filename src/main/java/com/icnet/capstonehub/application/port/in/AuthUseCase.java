package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.adapter.in.web.request.LoginRequest;
import com.icnet.capstonehub.application.port.in.result.UserResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface AuthUseCase {
    Optional<UserResult> findByEmail(String email);
    void login(HttpServletRequest request, HttpServletResponse response, LoginRequest body);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
