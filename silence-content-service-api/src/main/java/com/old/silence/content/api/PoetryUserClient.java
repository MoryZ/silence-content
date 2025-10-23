package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* PoetryUserFeign客户端
*/
@FeignClient(name = "content-service", contextId = "poetryUser", path = "/api/v1")
public interface PoetryUserClient extends PoetryUserService {
}