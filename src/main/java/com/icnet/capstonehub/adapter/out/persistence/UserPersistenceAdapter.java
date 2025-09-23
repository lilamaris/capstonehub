package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.mapper.UserEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.UserRepository;
import com.icnet.capstonehub.application.port.out.UserPort;
import com.icnet.capstonehub.domain.model.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPort {
    private final UserRepository userRepository;
    private final EntityManager em;

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream().map(UserEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email).map(UserEntityMapper::toDomain);
    }

    @Override
    public User update(User.Id id, User user) {
        return null;
    }

    @Override
    public void delete(User.Id id) {

    }

    @Override
    public User save(User user) {
        return UserEntityMapper.toDomain(userRepository.save(UserEntityMapper.toEntity(user)));
     }
}
