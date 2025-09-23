package com.icnet.capstonehub.adapter.out.persistence.mapper;

import com.icnet.capstonehub.adapter.out.persistence.entity.AccountEntity;
import com.icnet.capstonehub.domain.model.Account;
import com.icnet.capstonehub.domain.model.User;

public class AccountEntityMapper {
    public static Account toDomain(AccountEntity entity) {
        var id = new Account.Id(entity.getId());
        var userId = new User.Id(entity.getConnectedUser().getId());

        return Account.builder()
                .id(id)
                .provider(entity.getProvider())
                .providerId(entity.getProviderId())
                .passwordHash(entity.getPasswordHash())
                .connectedUserId(userId)
                .connectedAt(entity.getConnectedAt())
                .build();
    }
}
