package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryQuizQuestionsFeign客户端
 */
@FeignClient(name = "silence-content-service", contextId = "poetry-quiz-questions", path = "/api/v1")
public interface PoetryQuizQuestionsClient extends PoetryQuizQuestionsService {
}