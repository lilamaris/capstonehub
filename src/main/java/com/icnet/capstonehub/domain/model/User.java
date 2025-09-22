package com.icnet.capstonehub.domain.model;

import jakarta.transaction.NotSupportedException;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public record User(
    Id id,
    String name,
    String email,
    List<Account> connectedAccount
) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        Account credential = connectedAccount
                .stream()
                .filter(ac -> ac.provider().equals(Account.Type.CREDENTIAL))
                .findFirst().orElseThrow(() -> new IllegalStateException("no exists credential account"));
        return credential.passwordHash();
    }

    @Override
    public String getUsername() {
        return "";
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

    public record Id(UUID value) {}
}
