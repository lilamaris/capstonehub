package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.User;

import java.util.Optional;

public interface UserPort {
    Optional<User> findByEmail(String email);
    User save(User user);
}
