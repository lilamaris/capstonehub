package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    Optional<User> getById(User.Id id);
    Optional<User> getByEmail(String email);
    List<User> getAll();
    User update(User.Id id, User user);
    void delete(User.Id id);
    User save(User user);
}
