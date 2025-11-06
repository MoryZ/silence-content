package com.old.silence.content.infrastructure.ollama;


import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<Float> list = new ArrayList<>(embeddingArray.length);
        for (float value : embeddingArray) {
            list.add(value);
        }
        return list;
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

    /**
     * 生成文本内容
     * @param prompt 提示词
     * @param model 模型名称，如果为null则使用默认模型
     * @return 生成的文本
     */
    public String generateText(String prompt, String model) {
        String modelName = model != null ? model : ollamaProperties.defaultModel();
        
        // 创建用户消息（使用 Message.builder）
        OllamaApi.Message userMessage = OllamaApi.Message.builder(OllamaApi.Message.Role.USER)
                .content(prompt)
                .build();
        
        // 使用 Builder 构建 ChatRequest
        OllamaApi.ChatRequest request = OllamaApi.ChatRequest.builder(modelName)
                .messages(List.of(userMessage))
                .stream(false)
                .build();
        
        // 调用 chat API
        OllamaApi.ChatResponse response = ollamaApi.chat(request);
        
        // 提取响应内容
        if (response != null && response.message() != null && response.message().content() != null) {
            return response.message().content();
        }
        throw new RuntimeException("Failed to generate text: response is null or empty");
    }

    /**
     * 使用默认模型生成文本内容
     */
    public String generateText(String prompt) {
        return generateText(prompt, null);
    }
}
