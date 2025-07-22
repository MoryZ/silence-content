package com.old.silence.content;

import org.springframework.ai.autoconfigure.vectorstore.elasticsearch.ElasticsearchVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {ElasticsearchVectorStoreAutoConfiguration.class}) //排除 Elasticsearch 向量存储的自动配置类
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }
}
