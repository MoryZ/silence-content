package com.old.silence.content.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

/**
 * @author moryzang
 */
@Configuration(proxyBeanMethods = false)
public class AuditorAwareConfiguration {

    private static String getAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.isNull(authentication) && !Objects.isNull(authentication.getPrincipal())) {
            return authentication.getPrincipal().toString();
        }
        return "SYSTEM";
    }

    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.of(getAuditor());
    }
}
