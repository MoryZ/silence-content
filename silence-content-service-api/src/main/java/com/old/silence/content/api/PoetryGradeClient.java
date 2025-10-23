package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* PoetryGradeFeign客户端
*/
@FeignClient(name = "content-service", contextId = "poetryGrade", path = "/api/v1")
public interface PoetryGradeClient extends PoetryGradeService {
}