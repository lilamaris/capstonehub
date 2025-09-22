package com.icnet.capstonehub.adapter.in.web.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApplicationProperties applicationProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(customizer -> {
            customizer
                    .requestMatchers(HttpMethod.GET, "/api/v1/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/user").hasAnyRole("MANAGER", "ADMIN")
                    .anyRequest().permitAll();
        });

        http.exceptionHandling( customizer -> {
            customizer.authenticationEntryPoint(
                    (request, response, authException) -> {
                        response.sendError(401, "Unauthorized");
                    }
                );
        });

        http.cors(customizer -> {
            customizer.configurationSource(corsConfigurationSource());
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(applicationProperties.getAllowedOrigins());
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);
                return config;
            }
        };
    }
}
