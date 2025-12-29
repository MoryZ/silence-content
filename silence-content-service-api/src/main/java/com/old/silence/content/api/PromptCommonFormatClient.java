package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PromptCommonFormatFeign客户端
 */
@FeignClient(name = "silence-content-service", contextId = "prompt-common-format", path = "/api/v1")
public interface PromptCommonFormatClient extends PromptCommonFormatService {
}