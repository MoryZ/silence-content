package com.old.silence.content.console.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.console.api.config.llm.OllamaClient;

/**
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class TestResource {

    private final OllamaClient ollamaClient;

    public TestResource(OllamaClient ollamaClient) {
        this.ollamaClient = ollamaClient;
    }

    @GetMapping("/test")
    public String chat(@RequestParam String prompt) {
        return ollamaClient.invokeOllama(prompt);
    }
}
