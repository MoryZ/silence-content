package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.FeignClient;
import static com.old.silence.content.application.api.ContentContextUtils.APPLICATION_NAME;

/**
* PoetryQuizQuestionsFeign客户端
*/
@FeignClient(name = APPLICATION_NAME, contextId = "poetry-quiz-questions", path = "/api/v1")
public interface PoetryQuizQuestionsApplicationClient extends PoetryQuizQuestionsApplicationService {
}