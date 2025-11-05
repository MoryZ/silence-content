package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryUserStudySettingFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "poetryUserStudySetting", path = "/api/v1")
public interface PoetryUserStudySettingApplicationClient extends PoetryUserStudySettingApplicationService{
}