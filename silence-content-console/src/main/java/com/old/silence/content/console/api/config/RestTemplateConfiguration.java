package com.old.silence.content.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author moryzang
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplateForLlm() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30000);     // 连接超时 30秒
        factory.setReadTimeout(120000);       // 读取超时 120秒
        return new RestTemplate(factory);
    }
}
