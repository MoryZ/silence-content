package com.old.silence.content.infrastructure.ollama;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author moryzang
 */
@Configuration
@EnableConfigurationProperties(OllamaProperties.class)
public class OllamaConfig {


    @Bean
    public RestTemplate ollamaRestTemplate(OllamaProperties ollamaProperties, RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.rootUri(ollamaProperties.baseUrl())
                .build();
    }
}