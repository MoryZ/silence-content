package com.old.silence.content.console.api.config.llm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.old.silence.content.console.api.config.LlmProperties;
import com.old.silence.json.JacksonMapper;

import java.util.Map;

/**
 * @author moryzang
 */
@Component
public class LlmClient {

    private static final Logger log = LoggerFactory.getLogger(LlmClient.class);
    private final RestTemplate restTemplateForLlm;
    private final JacksonMapper jacksonMapper;
    private final String baseUrl;
    public LlmClient(RestTemplate restTemplateForLlm, JacksonMapper jacksonMapper, LlmProperties llmProperties) {
        this.restTemplateForLlm = restTemplateForLlm;
        this.jacksonMapper = jacksonMapper;
        this.baseUrl = llmProperties.getBaseUrl();
    }

    public String invokeLlm(String prompt) {

        log.info("开始调用llm 时间:{}", System.currentTimeMillis());
        var systemPrompt = "你是一名专业的语文老师，擅长根据诗词内容设计练习题，输出的内容必须是合法的 JSON 数组。";

        // 直接将系统提示和用户输入合并
        String fullPrompt = systemPrompt + "\n\n请处理以下请求：" + prompt;
        log.info("合并后的提示词:{}", fullPrompt);
        Map<String, Object> requestBody = Map.of(
                "prompt", fullPrompt
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 调用 API
        String apiUrl = baseUrl + "/chat";

        log.info("调用llm 参数:{}", jacksonMapper.toJson(entity));
        ResponseEntity<String> response = restTemplateForLlm.postForEntity(apiUrl, entity, String.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("LLM 请求失败，状态码: " + response.getStatusCode());
        }

        try {
            ChatCompletionApiResponse completionResponse = jacksonMapper.unwrap().readValue(response.getBody(), ChatCompletionApiResponse.class);
            if (completionResponse.getResponse() == null || completionResponse.getResponse().isEmpty()) {
                throw new RuntimeException("LLM 未返回任何内容");
            }
            return completionResponse.getResponse();

        } catch (Exception e) {
            log.error("解析 LLM 响应失败", e);
            throw new RuntimeException("解析 LLM 响应失败: " + e.getMessage(), e);
        }
    }
    private static class ChatCompletionApiResponse {
        private String response;
        private String status;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
