package com.old.silence.content.infrastructure.ollama;

import java.time.Duration;

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
        return restTemplateBuilder
                .rootUri(ollamaProperties.baseUrl())
                .setConnectTimeout(Duration.ofSeconds(30))  // 连接超时 30秒
                .setReadTimeout(Duration.ofMinutes(5))     // 读取超时 5分钟（大模型需要更长时间）
                .build();
    }
}