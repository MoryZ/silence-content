package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * ContentInteractionLogFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "contentInteractionLog", path = "/api/v1")
public interface ContentInteractionLogClient extends ContentInteractionLogService {
}