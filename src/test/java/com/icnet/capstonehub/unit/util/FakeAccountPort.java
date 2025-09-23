package com.icnet.capstonehub.unit.util;

import com.icnet.capstonehub.application.port.out.AccountPort;
import com.icnet.capstonehub.domain.model.Account;

import java.util.*;
import java.util.function.Supplier;

public class FakeAccountPort implements AccountPort {
    private final Map<UUID, Account> store = new HashMap<>();
    private final Supplier<UUID> idGenerator;

    public FakeAccountPort(Supplier<UUID> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Account save(Account account) {
        var id = Optional.ofNullable(account.id()).orElseGet(() -> new Account.Id(idGenerator.get()));

        var saved = account.toBuilder()
                .id(id)
                .build();

        store.put(id.value(), saved);
        return saved;
    }

    @Override
    public Optional<Account> getCredentialByUserEmail(String email) {
        return store.values().stream()
                .filter(a -> a.connectedUser().email().equals(email))
                .filter(a -> a.provider().equals(Account.Type.CREDENTIAL))
                .findFirst();
    }

    @Override
    public List<Account> getAccountByUserEmail(String email) {
        return store.values().stream()
                .filter(a -> a.connectedUser().email().equals(email))
                .toList();
    }
}
