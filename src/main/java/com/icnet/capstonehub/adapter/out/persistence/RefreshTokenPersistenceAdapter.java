package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.RefreshTokenEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.UserEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.RefreshTokenEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.RefreshTokenRepository;
import com.icnet.capstonehub.application.port.out.RefreshTokenPort;
import com.icnet.capstonehub.domain.model.RefreshToken;
import com.icnet.capstonehub.domain.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenPersistenceAdapter implements RefreshTokenPort {
    private final RefreshTokenRepository refreshTokenRepository;
    private final EntityManager em;

    @Override
    public Optional<RefreshToken> getByUserId(User.Id id) {
        return refreshTokenRepository.findByUserId(id.value()).map(RefreshTokenEntityMapper::toDomain);
    }

    @Override
    public Optional<RefreshToken> getByToken(String token) {
        return refreshTokenRepository.findByToken(token).map(RefreshTokenEntityMapper::toDomain);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        var id = Optional.ofNullable(refreshToken.id())
                .map(RefreshToken.Id::value)
                .orElse(null);
        var user = em.getReference(UserEntity.class, refreshToken.userId().value());

        var entity = RefreshTokenEntity.builder()
                .id(id)
                .user(user)
                .token(refreshToken.token())
                .expiredAt(refreshToken.expiredAt())
                .build();

        var saved = refreshTokenRepository.save(entity);
        return RefreshTokenEntityMapper.toDomain(saved);
    }

    @Override
    public void delete(String token) {
        var entity = refreshTokenRepository.findByToken(token).orElseThrow(EntityNotFoundException::new);
        refreshTokenRepository.delete(entity);
    }
}
