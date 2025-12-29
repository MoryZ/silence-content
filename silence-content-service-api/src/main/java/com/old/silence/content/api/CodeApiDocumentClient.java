package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * CodeApiDocumentFeign客户端
 */
@FeignClient(name = "silence-content-service", contextId = "code-api-document", path = "/api/v1")
public interface CodeApiDocumentClient extends CodeApiDocumentService {
}