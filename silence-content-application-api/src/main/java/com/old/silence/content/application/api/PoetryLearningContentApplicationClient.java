package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryLearningContentFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "poetryLearningContent", path = "/api/v1")
public interface PoetryLearningContentApplicationClient extends PoetryLearningContentApplicationService {
}