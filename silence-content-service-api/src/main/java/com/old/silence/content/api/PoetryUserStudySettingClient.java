package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryUserStudySettingFeign客户端
 */
@FeignClient(name = "content-service", contextId = "poetryUserStudySetting", path = "/api/v1")
public interface PoetryUserStudySettingClient extends PoetryUserStudySettingService {
}