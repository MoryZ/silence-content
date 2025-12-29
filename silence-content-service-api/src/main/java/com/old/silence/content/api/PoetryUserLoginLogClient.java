package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryUserLoginLogFeign客户端
 */
@FeignClient(name = "silence-content-service", contextId = "poetry-user-login-log", path = "/api/v1")
public interface PoetryUserLoginLogClient extends PoetryUserLoginLogService {
}