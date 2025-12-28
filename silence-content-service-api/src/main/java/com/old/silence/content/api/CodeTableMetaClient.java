package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* CodeTableMetaFeign客户端
*/
@FeignClient(name = "silence-content-service", contextId = "code-table-meta", path = "/api/v1")
public interface CodeTableMetaClient extends CodeTableMetaService {
}