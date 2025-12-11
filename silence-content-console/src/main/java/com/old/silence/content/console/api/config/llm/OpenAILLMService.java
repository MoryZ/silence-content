package com.old.silence.content.console.api.config.llm;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;

/**
 * OpenAI 大模型实现，使用 Chat Completions 接口。
 *
 * <p>为了保持可测试性与可维护性，基于 RestTemplate 实现 HTTP 调用，
 * 并在发生异常时提供可回退的基础实现。</p>
 *
 * @author moryzang
 */
@Service
@ConditionalOnProperty(name = "code-generator.llm.provider", havingValue = "OPENAI")
public class OpenAILLMService implements LLMService {

    private static final Logger log = LoggerFactory.getLogger(OpenAILLMService.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String model;
    private final double temperature;
    private final int maxTokens;

    public OpenAILLMService(RestTemplateBuilder builder,
                            @Value("${code-generator.llm.openai.api-key:}") String apiKey,
                            @Value("${code-generator.llm.openai.base-url:https://api.openai.com/v1}") String baseUrl,
                            @Value("${code-generator.llm.openai.model:gpt-4o-mini}") String model,
                            @Value("${code-generator.llm.temperature:0.3}") double temperature,
                            @Value("${code-generator.llm.max-tokens:4000}") int maxTokens) {
        this.restTemplate = builder.build();
        this.apiKey = apiKey;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.model = model;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
    }

    @Override
    public String generateCode(String prompt, CodeGenerationContext context) {
        if (!isConfigured()) {
            log.warn("OpenAI未配置API Key，返回回退内容");
            return buildFallbackCode(context, prompt);
        }

        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(model);
        request.setTemperature(temperature);
        request.setMaxTokens(maxTokens);
        request.setMessages(List.of(
                new ChatMessage("system", "你是一个经验丰富的Java开发工程师，负责根据输入生成高质量代码。"),
                new ChatMessage("user", prompt)
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatCompletionRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<ChatCompletionResponse> response = restTemplate.exchange(
                    baseUrl + "/chat/completions",
                    HttpMethod.POST,
                    entity,
                    ChatCompletionResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getContent();
            }
            log.warn("OpenAI返回非预期状态码: {}", response.getStatusCode());
        } catch (RestClientException ex) {
            log.error("调用OpenAI失败: {}", ex.getMessage(), ex);
        }

        return buildFallbackCode(context, prompt);
    }

    @Override
    public ApiDocument generateApiDocument(TableInfo tableInfo, String requirements) {
        ApiDocument document = new ApiDocument();
        document.setTableName(tableInfo.getTableName());

        // 利用大模型生成更加详细的规则描述
        String prompt = "请根据以下表结构和业务需求，提炼出接口编排规则：\n" +
                "表名: " + tableInfo.getTableName() + "\n" +
                "字段: " + tableInfo.getColumnInfos().stream()
                .map(column -> column.getOriginalName() + "(" + column.getType() + ")")
                .reduce((a, b) -> a + ", " + b).orElse("无") + "\n" +
                "业务需求: " + requirements + "\n" +
                "请以JSON格式输出，包含rules字段。";

        String response = generateCode(prompt, new CodeGenerationContext());
        return document;
    }

    @Override
    public String generateFrontendCode(ApiDocument apiDoc, String frontendType, FrontendConfig config) {
        String prompt = "请根据以下API文档生成" + frontendType + "前端页面脚手架，" +
                "使用UI库" + Objects.toString(config.getUiLibrary(), "Element Plus") + "。\n" +
                "API文档: " + apiDoc.getEndpoints();
        return generateCode(prompt, new CodeGenerationContext());
    }

    private boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank();
    }

    private String buildFallbackCode(CodeGenerationContext context, String prompt) {
        StringBuilder builder = new StringBuilder();
        builder.append("// LLM调用失败，返回基础模板\n");
        if (context != null && context.getClassName() != null) {
            builder.append("public class ").append(context.getClassName()).append("Fallback {")
                    .append("\n    // 原始提示词: ").append(prompt.replace('\n', ' '))
                    .append("\n}");
        } else {
            builder.append(prompt);
        }
        return builder.toString();
    }

    /**
     * OpenAI Chat Completion 请求对象。
     */
    @SuppressWarnings("unused")
    private static class ChatCompletionRequest {
        private String model;
        private List<ChatMessage> messages;
        private double temperature;
        @JsonProperty("max_tokens")
        private Integer maxTokens;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public List<ChatMessage> getMessages() {
            return messages;
        }

        public void setMessages(List<ChatMessage> messages) {
            this.messages = messages;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public Integer getMaxTokens() {
            return maxTokens;
        }

        public void setMaxTokens(Integer maxTokens) {
            this.maxTokens = maxTokens;
        }
    }

    @SuppressWarnings("unused")
    private static class ChatMessage {
        private String role;
        private String content;

        public ChatMessage() {
        }

        public ChatMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @SuppressWarnings("unused")
    private static class ChatCompletionResponse {
        private List<Choice> choices;

        public List<Choice> getChoices() {
            return choices;
        }

        public void setChoices(List<Choice> choices) {
            this.choices = choices;
        }

        public String getContent() {
            if (choices == null || choices.isEmpty()) {
                return "";
            }
            return choices.get(0).getMessage().getContent();
        }

        @SuppressWarnings("unused")
        private static class Choice {
            private ChatMessage message;

            public ChatMessage getMessage() {
                return message;
            }

            public void setMessage(ChatMessage message) {
                this.message = message;
            }
        }
    }
}