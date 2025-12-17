package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * UserInteractionLogFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "userInteractionLog", path = "/api/v1")
public interface UserInteractionLogClient extends UserInteractionLogService {
}