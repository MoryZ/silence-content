package com.old.silence.content.infrastructure.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * @author moryzang
 */
@Configuration(proxyBeanMethods = false)
public class AuditorAwareConfiguration {

    private static String getAuditor() {
        return "SYSTEM";
    }

    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.of(getAuditor());
    }
}
