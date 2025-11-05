package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryGradeFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "poetryGrade", path = "/api/v1")
public interface PoetryGradeApplicationClient extends PoetryGradeApplicationService {
}