package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* PromptTemplateFeign客户端
*/
@FeignClient(name = "silence-content-service", contextId = "prompt-template", path = "/api/v1")
public interface PromptTemplateClient extends PromptTemplateService {
}