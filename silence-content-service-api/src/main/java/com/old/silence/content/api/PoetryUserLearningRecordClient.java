package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* PoetryUserLearningRecordFeign客户端
*/
@FeignClient(name = "content-service", contextId = "poetryUserLearningRecord", path = "/api/v1")
public interface PoetryUserLearningRecordClient extends PoetryUserLearningRecordService {
}