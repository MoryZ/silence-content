package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 互动累计同步 Feign 客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "contentInteractionAccumulation", path = "/api/v1")
public interface ContentInteractionAccumulationClient extends ContentInteractionAccumulationService {
}