package com.old.silence.content.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moryzang
 */
public record OllamaRequest(
        @JsonProperty("model") String model,
        @JsonProperty("prompt") String prompt,
        @JsonProperty("stream") boolean stream
) {
    public OllamaRequest(String model, String prompt) {
        this(model, prompt, false);
    }
}