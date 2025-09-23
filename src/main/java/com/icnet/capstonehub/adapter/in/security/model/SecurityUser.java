package com.icnet.capstonehub.adapter.in.security.model;

import com.icnet.capstonehub.application.port.in.result.AccountResult;
import com.icnet.capstonehub.application.port.in.result.UserResult;
import com.icnet.capstonehub.domain.model.Account;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record SecurityUser(
        UserResult user
) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.role()));
    }

    @Override
    public String getPassword() {
        return user.connectedAccount()
                .stream()
                .filter(accountResult -> accountResult.provider().equals(Account.Type.CREDENTIAL))
                .findFirst()
                .map(AccountResult::passwordHash)
                .orElseThrow(() -> new BadCredentialsException("user doesn't have any credentials"));
    }

    @Override
    public String getUsername() {
        return user.email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
