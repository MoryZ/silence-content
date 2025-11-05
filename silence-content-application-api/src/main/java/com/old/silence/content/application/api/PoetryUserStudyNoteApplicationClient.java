package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryUserStudyNoteFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "poetryUserStudyNote", path = "/api/v1")
public interface PoetryUserStudyNoteApplicationClient extends PoetryUserStudyNoteApplicationService{
}