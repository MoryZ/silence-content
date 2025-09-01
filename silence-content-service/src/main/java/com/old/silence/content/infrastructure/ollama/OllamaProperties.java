package com.old.silence.content.infrastructure.ollama;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author moryzang
 */
@ConfigurationProperties(prefix = "spring.ollama")
public record OllamaProperties(
        String baseUrl,
        String defaultModel
) {
}