package com.old.silence.content.infrastructure.ollama;


import org.apache.commons.lang3.StringUtils;
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
    private final OllamaProperties ollamaProperties;

    public OllamaService(RestTemplate ollamaRestTemplate,
                         OllamaProperties ollamaProperties) {
        this.restTemplate = ollamaRestTemplate;
        this.ollamaProperties = ollamaProperties;
    }

    public OllamaResponse generate(OllamaRequest ollamaRequest) {
        var model = ollamaRequest.model();
        if (StringUtils.isBlank(ollamaRequest.model())) {
            model = ollamaProperties.defaultModel();
        }
        return generateWithResponse(ollamaRequest.prompt(), model);
    }

    public OllamaResponse generateWithResponse(String prompt, String model) {
        String url = "/api/generate";
        OllamaRequest request = new OllamaRequest(model, prompt);
        return restTemplate.postForObject(url, request, OllamaResponse.class);
    }
}
