package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.mapper.UserEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.UserRepository;
import com.icnet.capstonehub.application.port.out.UserPort;
import com.icnet.capstonehub.domain.model.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPort {
    private final UserRepository userRepository;
    private final EntityManager em;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserEntityMapper::toDomainLite);
    }

    @Override
    public User save(User user) {
        return UserEntityMapper.toDomainLite(userRepository.save(UserEntityMapper.toEntity(user)));
     }
}
