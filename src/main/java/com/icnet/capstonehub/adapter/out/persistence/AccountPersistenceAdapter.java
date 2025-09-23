package com.icnet.capstonehub.adapter.out.persistence;

import com.icnet.capstonehub.adapter.out.persistence.entity.UserEntity;
import com.icnet.capstonehub.adapter.out.persistence.mapper.AccountEntityMapper;
import com.icnet.capstonehub.adapter.out.persistence.repository.AccountRepository;
import com.icnet.capstonehub.application.port.out.AccountPort;
import com.icnet.capstonehub.domain.model.Account;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountPort {
    private final AccountRepository accountRepository;
    private final EntityManager em;

    @Override
    public Account save(Account account) {
        var entity = AccountEntityMapper.toEntity(account);

        UUID userId = account.connectedUser().id().value();
        entity.setConnectedUser(em.getReference(UserEntity.class, userId));

        var saved = accountRepository.save(entity);

        return AccountEntityMapper.toDomain(saved);
    }

    @Override
    public List<Account> getAccountByUserEmail(String email) {
        return accountRepository.findAccountByUserEmail(email)
                .stream()
                .map(AccountEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Account> getCredentialByUserEmail(String email) {
        return accountRepository.findCredentialByUserEmail(email).map(AccountEntityMapper::toDomain);
    }
}
