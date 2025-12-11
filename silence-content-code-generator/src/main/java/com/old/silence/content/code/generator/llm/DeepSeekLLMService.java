package com.old.silence.content.code.generator.llm;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
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
 * DeepSeek 大模型实现，兼容 OpenAI Chat Completions 协议。
 */
@Service
@ConditionalOnProperty(name = "code-generator.llm.provider", havingValue = "DEEPSEEK")
public class DeepSeekLLMService implements LLMService {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekLLMService.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String model;
    private final double temperature;
    private final int maxTokens;

    public DeepSeekLLMService(RestTemplateBuilder builder,
                              @Value("${code-generator.llm.deepseek.api-key:sk-0e6d0fcc46c14f528e2ac411a7868c6d}") String apiKey,
                              @Value("${code-generator.llm.deepseek.base-url:https://api.deepseek.com}") String baseUrl,
                              @Value("${code-generator.llm.deepseek.model:deepseek-coder}") String model,
                              @Value("${code-generator.llm.temperature:0.3}") double temperature,
                              @Value("${code-generator.llm.max-tokens:4000}") int maxTokens) {
        this.restTemplate = builder.build();
        // 优先使用环境变量，如果没有则使用配置文件的值
        String envApiKey = System.getenv("DEEPSEEK_API_KEY");
        this.apiKey = StringUtils.isNotBlank(envApiKey) ? envApiKey : apiKey;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.model = model;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
        
        // 记录配置状态（不显示完整API Key）
        if (StringUtils.isNotBlank(this.apiKey)) {
            String maskedKey = this.apiKey.length() > 8 
                ? this.apiKey.substring(0, 4) + "..." + this.apiKey.substring(this.apiKey.length() - 4)
                : "***";
            log.info("DeepSeek配置初始化 - API Key已配置: {}", maskedKey);
        } else {
            log.warn("DeepSeek配置初始化 - API Key未配置，请检查配置文件或环境变量");
        }
    }

    @Override
    public String generateCode(String prompt, CodeGenerationContext context) {
        if (!isConfigured()) {
            log.warn("DeepSeek未配置API Key，返回回退内容");
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
            log.warn("DeepSeek返回非预期状态码: {}", response.getStatusCode());
        } catch (RestClientException ex) {
            log.error("调用DeepSeek失败: {}", ex.getMessage(), ex);
        }

        return buildFallbackCode(context, prompt);
    }

    @Override
    public ApiDocument generateApiDocument(TableInfo tableInfo, String requirements) {
        ApiDocument document = new ApiDocument();
        document.setTableName(tableInfo.getTableName());

        String prompt = "请根据以下表结构生成接口文档规则，输出JSON\n" +
                "表名: " + tableInfo.getTableName() + "\n" +
                "字段: " + tableInfo.getColumnInfos().stream()
                .map(column -> column.getOriginalName() + "(" + column.getType() + ")")
                .reduce((a, b) -> a + ", " + b).orElse("无") + "\n" +
                "需求: " + requirements;

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
        boolean configured = StringUtils.isNotBlank(apiKey) && 
                            !apiKey.equals("your-deepseek-api-key") &&
                            !apiKey.equals("sk-0e6d0fcc46c14f528e2ac411a7868c6d"); // 移除硬编码的默认值检查
        if (!configured) {
            log.warn("DeepSeek API Key未正确配置，当前值: {}", 
                    apiKey != null ? (apiKey.length() > 10 ? apiKey.substring(0, 10) + "..." : apiKey) : "null");
        }
        return configured;
    }

    private String buildFallbackCode(CodeGenerationContext context, String prompt) {
        StringBuilder builder = new StringBuilder();
        builder.append("// DeepSeek 调用失败，返回基础模板\n");
        if (context != null && context.getClassName() != null) {
            builder.append("public class ").append(context.getClassName()).append("Fallback {")
                    .append("\n    // 原始提示词: ").append(prompt.replace('\n', ' '))
                    .append("\n}");
        } else {
            builder.append(prompt);
        }
        return builder.toString();
    }

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
            return choices.getFirst().getMessage().getContent();
        }

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
