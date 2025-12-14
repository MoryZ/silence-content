package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * CodeFileTemplateFeign客户端
 */
@FeignClient(name = "silence-content-service", contextId = "code-file-template", path = "/api/v1")
public interface CodeFileTemplateClient extends CodeFileTemplateService {
}