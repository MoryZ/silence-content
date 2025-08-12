package com.old.silence.content.infrastructure.ollama;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.old.silence.content.api.dto.OllamaRequest;
import com.old.silence.content.api.vo.OllamaResponse;

/**
 * @author moryzang
 */
@Service
@EnableConfigurationProperties(OllamaProperties.class)
public class OllamaService {

    private final RestTemplate restTemplate;

    public OllamaService(RestTemplate ollamaRestTemplate) {
        this.restTemplate = ollamaRestTemplate;
    }

    public OllamaResponse generate(OllamaRequest ollamaRequest) {
        return generateWithResponse(ollamaRequest.prompt(), ollamaRequest.model());
    }

    public OllamaResponse generateWithResponse(String prompt, String model) {
        String url = "/api/generate";
        OllamaRequest request = new OllamaRequest(model, prompt);
        return restTemplate.postForObject(url, request, OllamaResponse.class);
    }
}
