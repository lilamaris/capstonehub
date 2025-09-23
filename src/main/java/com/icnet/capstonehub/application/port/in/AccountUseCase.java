package com.icnet.capstonehub.application.port.in;

import com.icnet.capstonehub.application.port.in.command.SignupCredentialCommand;
import com.icnet.capstonehub.application.port.in.result.AccountResult;
import com.icnet.capstonehub.application.port.in.result.UserResult;

import java.util.List;
import java.util.Optional;

public interface AccountUseCase {
    List<AccountResult> getAccountByUserEmail(String email);
    AccountResult getCredentialByUserEmail(String email);
    UserResult signupCredential(SignupCredentialCommand command);
}
