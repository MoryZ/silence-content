package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryUserLearningStatsFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "poetryUserLearningStats", path = "/api/v1")
public interface PoetryUserLearningStatsClient extends PoetryUserLearningStatsService {
}