package com.icnet.capstonehub.application.port.out;

import com.icnet.capstonehub.domain.model.Account;

public interface AccountPort {
    Account save(Account account);
}
