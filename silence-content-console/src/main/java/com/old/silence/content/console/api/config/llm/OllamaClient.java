package com.old.silence.content.console.api.config.llm;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.old.silence.content.console.api.config.OllamaProperties;
import com.old.silence.json.JacksonMapper;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author moryzang
 */
@Component
public class OllamaClient {

    private static final Logger log = LoggerFactory.getLogger(OllamaClient.class);
    private final RestTemplate restTemplateForOllama;
    private final JacksonMapper jacksonMapper;
    private final String baseUrl;
    private final String modelName; // 添加模型名称
    public OllamaClient(RestTemplate restTemplateForOllama,
                        JacksonMapper jacksonMapper,
                        OllamaProperties ollamaProperties) {
        this.restTemplateForOllama = restTemplateForOllama;
        this.jacksonMapper = jacksonMapper;
        this.baseUrl = ollamaProperties.getBaseUrl();
        this.modelName = ollamaProperties.getModel();
    }

    public String invokeOllama(String prompt) {
        log.info("开始调用Ollama 时间:{}, prompt:{}", Instant.now().toString(), prompt);

        var requestBody = getStringObjectMap(prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 调用Ollama生成API
        String apiUrl = baseUrl + "/api/generate"; // Ollama的生成API端点

        log.info("调用Ollama 参数:{}", jacksonMapper.toJson(entity));

        try {
            ResponseEntity<String> response = restTemplateForOllama.postForEntity(apiUrl, entity, String.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("Ollama 请求失败，状态码: " + response.getStatusCode());
            }

            log.info("Ollama响应: {}", response.getBody());

            // 解析Ollama响应
            OllamaApiResponse ollamaResponse = jacksonMapper.unwrap().readValue(response.getBody(), OllamaApiResponse.class);

            if (ollamaResponse.getResponse() == null || ollamaResponse.getResponse().isEmpty()) {
                throw new RuntimeException("Ollama 未返回任何内容");
            }

            log.info("Ollama 调用成功，返回内容长度: {}", ollamaResponse.getResponse().length());
            return ollamaResponse.getResponse();

        } catch (Exception e) {
            log.error("调用Ollama 失败", e);
            throw new RuntimeException("调用Ollama: " + e.getMessage(), e);
        }
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(String prompt) {

        // 构建Ollama API请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", modelName); // 必须指定模型
        requestBody.put("prompt", prompt);

        requestBody.put("stream", false); // 非流式响应

        // 可选：设置生成参数
        Map<String, Object> options = new HashMap<>();
        options.put("temperature", 0.9); // 控制创造性
        options.put("top_p", 0.9); // 核采样
        options.put("top_k", 40); // 增加候选词数量
        options.put("repeat_penalty", 1.5);  // 增加重复惩罚，避免固定内容
        options.put("seed", new Random().nextInt(1000000));
        options.put("max_tokens", 4096); // 最大生成长度
        requestBody.put("options", options);
        return requestBody;
    }

    // Ollama API响应格式
    private static class OllamaApiResponse {
        private String model;
        private String created_at;
        private String response;
        private boolean done;
        private List<Integer> context;
        private Long total_duration;
        private Long load_duration;
        private Integer prompt_eval_count;
        private Integer prompt_eval_duration;
        private Integer eval_count;
        private Long eval_duration;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getResponse() {
            return response;
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

        public List<Integer> getContext() {
            return context;
        }

        public void setContext(List<Integer> context) {
            this.context = context;
        }

        public Long getTotal_duration() {
            return total_duration;
        }

        public void setTotal_duration(Long total_duration) {
            this.total_duration = total_duration;
        }

        public Long getLoad_duration() {
            return load_duration;
        }

        public void setLoad_duration(Long load_duration) {
            this.load_duration = load_duration;
        }

        public Integer getPrompt_eval_count() {
            return prompt_eval_count;
        }

        public void setPrompt_eval_count(Integer prompt_eval_count) {
            this.prompt_eval_count = prompt_eval_count;
        }

        public Integer getPrompt_eval_duration() {
            return prompt_eval_duration;
        }

        public void setPrompt_eval_duration(Integer prompt_eval_duration) {
            this.prompt_eval_duration = prompt_eval_duration;
        }

        public Integer getEval_count() {
            return eval_count;
        }

        public void setEval_count(Integer eval_count) {
            this.eval_count = eval_count;
        }

        public Long getEval_duration() {
            return eval_duration;
        }

        public void setEval_duration(Long eval_duration) {
            this.eval_duration = eval_duration;
        }
    }


}
