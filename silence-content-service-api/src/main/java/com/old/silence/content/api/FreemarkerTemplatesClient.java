package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * FreemarkerTemplatesFeign客户端
 */
@FeignClient(name = "silence-content-service", contextId = "freemarker-templates", path = "/api/v1")
public interface FreemarkerTemplatesClient extends FreemarkerTemplatesService {
}