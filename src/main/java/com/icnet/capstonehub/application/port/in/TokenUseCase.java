package com.icnet.capstonehub.application.port.in;

import java.util.UUID;

public interface TokenUseCase {
    String getUserRole(String token);
    String getUserEmail(String token);
    UUID issueRefreshToken(UUID userId);
    String issueAccessToken(String email, String role);
    String reissue(UUID token);
    Boolean validateAccessToken(String token);
}
