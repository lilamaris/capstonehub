package com.icnet.capstonehub.adapter.in.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager {
    private final CredentialProvider credentialProvider;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(credentialProvider));
    }
}
