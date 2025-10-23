package com.icnet.capstonehub.adapter.in.security.service;

import com.icnet.capstonehub.adapter.in.security.model.SecurityUser;
import com.icnet.capstonehub.application.port.in.result.AccountResult;
import com.icnet.capstonehub.application.port.in.result.UserResult;
import com.icnet.capstonehub.domain.model.User;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class SecurityAuditorAware implements AuditorAware<UUID> {
    @Override @Nonnull
    public Optional<UUID> getCurrentAuditor() {

        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SecurityUser.class::cast)
                .map(SecurityUser::account)
                .map(AccountResult::user)
                .map(UserResult::id)
                .map(User.Id::value);
    }
}
