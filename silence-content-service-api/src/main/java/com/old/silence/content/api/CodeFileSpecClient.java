package com.old.silence.content.api;


import org.springframework.cloud.openfeign.FeignClient;

/**
* CodeFileSpecFeign客户端
*/
@FeignClient(name = "silence-content-service", contextId = "code-file-spec", path = "/api/v1")
public interface CodeFileSpecClient extends CodeFileSpecService {
}