package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryDailyStudyPlanFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "poetryDailyStudyPlan", path = "/api/v1")
public interface PoetryDailyStudyPlanApplicationClient extends PoetryDailyStudyPlanApplicationService {
}