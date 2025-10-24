package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryLearningContentFeign客户端
 */
@FeignClient(name = "content-service", contextId = "poetryLearningContent", path = "/api/v1")
public interface PoetryLearningContentClient extends PoetryLearningContentService {
}