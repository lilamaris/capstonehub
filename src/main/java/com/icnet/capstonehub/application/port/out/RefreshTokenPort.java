package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.RefreshToken;
import com.icnet.capstonehub.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenPort {
    Optional<RefreshToken> getByUserId(User.Id id);
    Optional<RefreshToken> getByToken(UUID token);
    RefreshToken save(RefreshToken refreshToken);
    void delete(UUID token);
}
