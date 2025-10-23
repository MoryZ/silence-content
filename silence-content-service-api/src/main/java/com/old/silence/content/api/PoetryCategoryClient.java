package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* PoetryCategoryFeign客户端
*/
@FeignClient(name = "content-service", contextId = "poetryCategory", path = "/api/v1")
public interface PoetryCategoryClient extends PoetryCategoryService {
}