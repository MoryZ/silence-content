package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* PoetryUserStudyNoteFeign客户端
*/
@FeignClient(name = "content-service", contextId = "poetryUserStudyNote", path = "/api/v1")
public interface PoetryUserStudyNoteClient extends PoetryUserStudyNoteService {
}