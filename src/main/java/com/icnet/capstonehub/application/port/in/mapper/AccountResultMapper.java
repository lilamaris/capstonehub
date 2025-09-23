package com.icnet.capstonehub.application.port.in.mapper;

import com.icnet.capstonehub.application.port.in.result.AccountResult;
import com.icnet.capstonehub.domain.model.Account;

public class AccountResultMapper {
    public static AccountResult toResult(Account domain) {
        return AccountResult.builder()
                .id(domain.id().value())
                .provider(domain.provider())
                .providerId(domain.providerId())
                .passwordHash(domain.passwordHash())
                .user(UserResultMapper.toResult(domain.connectedUser()))
                .build();
    }
}
