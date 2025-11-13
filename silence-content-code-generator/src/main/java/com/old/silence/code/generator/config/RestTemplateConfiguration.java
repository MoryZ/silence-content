package com.old.silence.code.generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author moryzang
 */
@Configuration
public class RestTemplateConfiguration {

     @Bean
     public RestTemplate restTemplateForOllamaLLM() {
         SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
         factory.setConnectTimeout(Duration.ofSeconds(30));     // 连接超时30秒
         factory.setReadTimeout(Duration.ofMinutes(5));         // 读取超时5分钟

         return new RestTemplate(factory);
     }
}
