package com.icnet.capstonehub.adapter.in.web.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
public class ApplicationProperties {
    private List<String> allowedOrigins;
    private String applicationName;
    private String baseUrl;
}
