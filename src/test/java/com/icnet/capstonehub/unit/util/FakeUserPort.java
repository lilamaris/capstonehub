package com.icnet.capstonehub.unit.util;

import com.icnet.capstonehub.application.port.out.UserPort;
import com.icnet.capstonehub.domain.model.User;

import java.util.*;
import java.util.function.Supplier;


public class FakeUserPort implements UserPort {
    private final Map<UUID, User> store = new HashMap<>();
    private final Supplier<UUID> idGenerator;

    public FakeUserPort(Supplier<UUID> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public User save(User user) {
        var id = Optional.ofNullable(user.id()).orElseGet(() -> new User.Id(idGenerator.get()));

        var saved = user.toBuilder()
                .id(id)
                .build();

        store.put(id.value(), saved);
        return saved;
    }

    @Override
    public void delete(User.Id id) {

    }

    @Override
    public User update(User.Id id, User user) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return store.values().stream()
                .filter(u -> u.email().equals(email))
                .findFirst();
    }
}
