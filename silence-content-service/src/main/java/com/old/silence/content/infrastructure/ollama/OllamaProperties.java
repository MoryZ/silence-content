package com.old.silence.content.infrastructure.ollama;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author moryzang
 */
@ConfigurationProperties(prefix = "spring.ollama")
public record OllamaProperties(
        String baseUrl,
        String defaultModel,
        String generationModel
) {
    /**
     * 获取生成模型名称，如果未配置则使用默认模型
     */
    public String getGenerationModel() {
        return generationModel != null ? generationModel : defaultModel;
    }
}