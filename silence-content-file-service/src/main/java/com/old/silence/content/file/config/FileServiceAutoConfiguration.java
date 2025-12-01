package com.old.silence.content.file.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import com.old.silence.autoconfigure.cos.CosTemplate;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.file.factory.CosStrategyFactory;
import com.old.silence.content.file.factory.FileStorageFactory;
import com.old.silence.content.file.factory.FileStorageStrategy;
import com.old.silence.content.file.factory.MinioStrategyFactory;

/**
 * @author moryzang
 */
@AutoConfiguration
@EnableConfigurationProperties(FileStorageConfigurations.class)
@ConditionalOnProperty(prefix = FileStorageConfigurations.PREFIX,
        name = {"type"})
public class FileServiceAutoConfiguration {

    @Bean
    FileStorageConfigurations fileStorageConfigurations() {
        return new FileStorageConfigurations();
    }

    @Bean
    @ConditionalOnProperty(name = "file.storage.type", havingValue = "COS")
    @ConditionalOnClass(CosTemplate.class)
    CosStrategyFactory cosStrategyFactory(CosTemplate cosTemplate) {
        return new CosStrategyFactory(cosTemplate);
    }

    @Bean
    @ConditionalOnProperty(name = "file.storage.type", havingValue = "MINIO")
    @ConditionalOnClass(MinioTemplate.class)
    MinioStrategyFactory minioStrategyFactory(MinioTemplate minioTemplate) {
        return new MinioStrategyFactory(minioTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public FileStorageFactory fileStorageFactory(FileStorageConfigurations fileStorageConfigurations, ObjectProvider<FileStorageStrategy> objectProvider) {
        return new FileStorageFactory(fileStorageConfigurations, objectProvider);
    }
}