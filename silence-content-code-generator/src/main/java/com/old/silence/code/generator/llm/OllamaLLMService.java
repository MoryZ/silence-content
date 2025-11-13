package com.old.silence.code.generator.llm;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.old.silence.code.generator.model.ApiDocument;
import com.old.silence.code.generator.model.TableInfo;

/**
 * 本地 Ollama 大模型服务实现。
 *
 * <p>通过 HTTP 调用本地 Ollama 服务生成代码或文档，
 * 并在出现异常时提供基础回退。</p>
 */
@Service
@ConditionalOnProperty(name = "code-generator.llm.provider", havingValue = "OLLAMA")
public class OllamaLLMService implements LLMService {

    private static final Logger log = LoggerFactory.getLogger(OllamaLLMService.class);

    private final RestTemplate restTemplateForOllamaLLM;
    private final String baseUrl;
    private final String model;

    public OllamaLLMService(RestTemplate restTemplateForOllamaLLM,
                            @Value("${code-generator.llm.ollama.base-url:http://localhost:11434}") String baseUrl,
                            @Value("${code-generator.llm.ollama.model:deepseek-coder:6.7b-instruct-q4_K_M}") String model) {
        this.restTemplateForOllamaLLM = restTemplateForOllamaLLM;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.model = model;
    }

    @Override
    public String generateCode(String prompt, CodeGenerationContext context) {
        OllamaGenerateRequest request = new OllamaGenerateRequest();
        request.setModel(model);
        request.setPrompt(prompt);
        request.setStream(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<OllamaGenerateResponse> response = restTemplateForOllamaLLM.postForEntity(
                    baseUrl + "/api/generate",
                    new HttpEntity<>(request, headers),
                    OllamaGenerateResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getResponse();
            }
            log.warn("Ollama返回非预期状态码: {}", response.getStatusCode());
        } catch (RestClientException ex) {
            log.error("调用Ollama失败: {}", ex.getMessage(), ex);
        }

        return buildFallbackCode(context, prompt);
    }

    @Override
    public ApiDocument generateApiDocument(TableInfo tableInfo, String requirements) {
        ApiDocument document = new ApiDocument();
        document.setTableName(tableInfo.getTableName());

        String prompt = "请基于以下表结构生成接口文档要点，输出JSON：\n" +
                "表名: " + tableInfo.getTableName() + "\n" +
                "需求: " + requirements;
        String response = generateCode(prompt, new CodeGenerationContext());
        document.setRules(Map.of("llmResponse", response));
        return document;
    }

    @Override
    public String generateFrontendCode(ApiDocument apiDoc, String frontendType, FrontendConfig config) {
        String prompt = "根据以下接口文档生成" + frontendType + "前端代码骨架：\n" +
                apiDoc.getEndpoints();
        return generateCode(prompt, new CodeGenerationContext());
    }

    private String buildFallbackCode(CodeGenerationContext context, String prompt) {
        String className = context != null ? context.getClassName() : null;
        StringBuilder builder = new StringBuilder();
        builder.append("// Ollama 调用失败，返回基础模板\n");
        if (className != null) {
            builder.append("public class ").append(className).append("Fallback {")
                    .append("\n    // prompt: ").append(prompt.replace('\n', ' '))
                    .append("\n}");
        } else {
            builder.append(prompt);
        }
        return builder.toString();
    }

    @SuppressWarnings("unused")
    private static class OllamaGenerateRequest {
        private String model;
        private String prompt;
        private boolean stream;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public boolean isStream() {
            return stream;
        }

        public void setStream(boolean stream) {
            this.stream = stream;
        }
    }

    @SuppressWarnings("unused")
    private static class OllamaGenerateResponse {
        private String response;
        @JsonProperty("done")
        private boolean done;

        public String getResponse() {
            return response != null ? response : "";
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }
}