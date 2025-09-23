package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountPort {
    List<Account> getAccountByUserEmail(String email);
    Optional<Account> getCredentialByUserEmail(String email);
    Account save(Account account);
}
