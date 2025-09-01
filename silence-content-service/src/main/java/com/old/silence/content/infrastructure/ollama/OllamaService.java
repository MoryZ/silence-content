package com.old.silence.content.infrastructure.ollama;


import reactor.core.publisher.Mono;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.old.silence.content.api.dto.OllamaRequest;
import com.old.silence.content.api.vo.OllamaResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author moryzang
 */
@Service
@EnableConfigurationProperties(OllamaProperties.class)
public class OllamaService {

    private final OllamaApi ollamaApi;
    private final OllamaProperties ollamaProperties;

    public OllamaService(OllamaApi ollamaApi, OllamaProperties ollamaProperties) {
        this.ollamaApi = ollamaApi;
        this.ollamaProperties = ollamaProperties;
    }

    /**
     * 获取单个文本的嵌入向量
     */
    public float[] getEmbedding(String text) {
        OllamaApi.EmbeddingsRequest request = new OllamaApi.EmbeddingsRequest(ollamaProperties.defaultModel(), text);
        OllamaApi.EmbeddingsResponse response = ollamaApi.embed(request);

        if (response != null && response.embeddings() != null && !response.embeddings().isEmpty()) {
            return response.embeddings().get(0);
        }
        throw new RuntimeException("Failed to get embedding");
    }

    /**
     * 获取嵌入向量并转换为List<Float>
     */
    public List<Float> getEmbeddingAsList(String text) {
        float[] embeddingArray = getEmbedding(text);
        return Arrays.stream(embeddingArray)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * 批量获取多个文本的嵌入向量
     */
    public List<float[]> getBatchEmbedding(List<String> texts) {
        OllamaApi.EmbeddingsRequest request = new OllamaApi.EmbeddingsRequest(
                ollamaProperties.defaultModel(),
                texts,
                null,  // keepAlive
                null,  // options
                null   // truncate
        );

        OllamaApi.EmbeddingsResponse response = ollamaApi.embed(request);
        return response.embeddings();
    }

    /**
     * 获取模型信息
     */
    public OllamaApi.ListModelResponse listModels() {
        return ollamaApi.listModels();
    }

    /**
     * 检查模型是否可用
     */
    public boolean isModelAvailable(String modelName) {
        OllamaApi.ListModelResponse models = listModels();
        return models.models().stream()
                .anyMatch(model -> model.name().equals(modelName));
    }
}
