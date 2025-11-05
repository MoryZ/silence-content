package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryUserLearningRecordFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "poetryUserLearningRecord", path = "/api/v1")
public interface PoetryUserLearningRecordApplicationClient extends PoetryUserLearningRecordApplicationService {
}