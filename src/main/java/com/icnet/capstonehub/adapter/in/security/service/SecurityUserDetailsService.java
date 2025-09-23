package com.icnet.capstonehub.adapter.in.security.service;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.application.port.in.AccountUseCase;
import com.icnet.capstonehub.application.port.in.result.AccountResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final AccountUseCase accountUseCase;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountResult accountResult = accountUseCase.getCredentialByUserEmail(email);

        return new SecurityUser(accountResult);
    }
}
