package com.old.silence.content.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moryzang
 */
public record OllamaResponse(
        @JsonProperty("response") String response,
        @JsonProperty("done") boolean done
) {
}