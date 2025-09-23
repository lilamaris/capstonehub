package com.icnet.capstonehub.application.service;

import com.icnet.capstonehub.application.port.in.AccountUseCase;
import com.icnet.capstonehub.application.port.in.command.SignupCredentialCommand;
import com.icnet.capstonehub.application.port.in.mapper.AccountResultMapper;
import com.icnet.capstonehub.application.port.in.mapper.UserResultMapper;
import com.icnet.capstonehub.application.port.in.result.AccountResult;
import com.icnet.capstonehub.application.port.in.result.UserResult;
import com.icnet.capstonehub.application.port.out.AccountPort;
import com.icnet.capstonehub.application.port.out.UserPort;
import com.icnet.capstonehub.domain.model.Account;
import com.icnet.capstonehub.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements AccountUseCase {
    private final UserPort userPort;
    private final AccountPort accountPort;

    @Override
    public AccountResult getCredentialByUserEmail(String email) {
        return accountPort.getCredentialByUserEmail(email).map(AccountResultMapper::toResult)
                .orElseThrow(() -> new BadCredentialsException("credential not exists"));
    }

    @Override
    public List<AccountResult> getAccountByUserEmail(String email) {
        return accountPort.getAccountByUserEmail(email)
                .stream()
                .map(AccountResultMapper::toResult)
                .toList();
    }

    @Override
    public UserResult signupCredential(SignupCredentialCommand command) {
        userPort.getByEmail(command.email()).ifPresent(u -> { throw new IllegalStateException("email already registered");});
        var user = User.builder()
                .name(command.name())
                .email(command.email())
                .role(User.Role.STUDENT)
                .build();
        var savedUser = userPort.save(user);

        var account = Account.withCredential(command.passwordHash(), savedUser, LocalDateTime.now());
        accountPort.save(account);

        return UserResultMapper.toResult(savedUser);
    }
}
