package com.old.silence.content.infrastructure.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

/**
 * @author moryzang
 */
@Configuration
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {

    @NotNull
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("14.103.187.231:9200")
                .build();
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        return ElasticsearchClients.createImperative(clientConfiguration());
    }
}
