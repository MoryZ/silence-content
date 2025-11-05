package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* PoetryAnswerRecordsFeign客户端
*/
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "poetry-answer-records", path = "/api/v1")
public interface PoetryAnswerRecordsApplicationClient extends PoetryAnswerRecordsApplicationService {
}