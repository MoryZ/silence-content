package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* PoetryAnswerRecordsFeign客户端
*/
@FeignClient(name = "silence-content-service", contextId = "poetry-answer-records", path = "/api/v1")
public interface PoetryAnswerRecordsClient extends PoetryAnswerRecordsService {
}