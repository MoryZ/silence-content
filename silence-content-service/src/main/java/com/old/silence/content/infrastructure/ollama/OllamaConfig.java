package com.old.silence.content.infrastructure.ollama;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author moryzang
 */
@Configuration
@EnableConfigurationProperties(OllamaProperties.class)
public class OllamaConfig {


    @Bean
    public OllamaApi ollamaApi(OllamaProperties ollamaProperties) {
        var baseUrl = ollamaProperties.baseUrl();
        Consumer<HttpHeaders> defaultHeaders = (headers) -> {
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        };

        // 配置带有超时的 RestClient
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
        requestFactory.setReadTimeout(Duration.ofSeconds(60));

        RestClient.Builder restClientBuilder = RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl(baseUrl)
                .defaultHeaders(defaultHeaders);

        // 配置 WebClient
        WebClient.Builder webClientBuilder = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(defaultHeaders);

        org.springframework.web.client.ResponseErrorHandler responseErrorHandler =
                new org.springframework.web.client.DefaultResponseErrorHandler();

        return OllamaApi.builder()
                .baseUrl(baseUrl)
                .restClientBuilder(restClientBuilder)
                .webClientBuilder(webClientBuilder)
                .responseErrorHandler(responseErrorHandler)
                .build();
    }
}