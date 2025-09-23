package com.icnet.capstonehub.adapter.in.security.service;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.application.port.in.AuthUseCase;
import com.icnet.capstonehub.application.port.in.result.UserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final AuthUseCase authUseCase;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserResult userResult = authUseCase.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("cannot find user with email " + email));

        return new SecurityUser(userResult);
    }
}
