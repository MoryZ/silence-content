package com.old.silence.content.infrastructure.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStoreOptions;
import org.springframework.ai.vectorstore.elasticsearch.SimilarityFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author moryzang
 */
@Configuration
public class EsRagConfig {

    @Bean
    public RestClient restClient() {
        //return RestClient.builder(new HttpHost("14.103.187.231", 9200, "http")) // 配置 ES 地址和端口
        return RestClient.builder(new HttpHost("localhost", 9200, "http")) // 配置 ES 地址和端口
                .build();
    }

    @Bean
    public VectorStore vectorStore(RestClient restClient, EmbeddingModel embeddingModel) {
        ElasticsearchVectorStoreOptions options = new ElasticsearchVectorStoreOptions();
        options.setIndexName("custom-index"); // 设置索引名称
        options.setSimilarity(SimilarityFunction.cosine);
        options.setDimensions(1024); // 设置向量维度

        return ElasticsearchVectorStore.builder(restClient, embeddingModel)
                .options(options)
                .initializeSchema(true) // 没有索引时自动创建索引
                .batchingStrategy(new TokenCountBatchingStrategy())
                .build();
    }

}