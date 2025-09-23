package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.AccountEntity;
import com.icnet.capstonehub.domain.model.Account;
import com.icnet.capstonehub.domain.model.User;

import java.util.Optional;

public class AccountEntityMapper {
    public static AccountEntity toEntity(Account domain) {
        var id = Optional.ofNullable(domain.id())
                .map(Account.Id::value)
                .orElse(null);

        return AccountEntity.builder()
                .id(id)
                .provider(domain.provider())
                .providerId(domain.providerId())
                .connectedUser(UserEntityMapper.toEntity(domain.connectedUser()))
                .connectedAt(domain.connectedAt())
                .passwordHash(domain.passwordHash())
                .build();
    }
    public static Account toDomain(AccountEntity entity) {
        var id = new Account.Id(entity.getId());

        return Account.builder()
                .id(id)
                .provider(entity.getProvider())
                .providerId(entity.getProviderId())
                .passwordHash(entity.getPasswordHash())
                .connectedUser(UserEntityMapper.toDomain(entity.getConnectedUser()))
                .connectedAt(entity.getConnectedAt())
                .build();
    }
}
