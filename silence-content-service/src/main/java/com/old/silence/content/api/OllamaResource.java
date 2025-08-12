package com.old.silence.content.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.OllamaRequest;
import com.old.silence.content.api.vo.OllamaResponse;
import com.old.silence.content.infrastructure.ollama.OllamaService;

/**
 * @author moryzang
 */
public class OllamaResource {

    private final OllamaService ollamaService;

    public OllamaResource(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/ask")
    public OllamaResponse askQuestion(@RequestBody OllamaRequest ollamaRequest) {
        return ollamaService.generate(ollamaRequest);
    }
}
