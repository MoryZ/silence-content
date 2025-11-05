package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * PoetryUserFavoriteFeign客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "poetryUserFavorite", path = "/api/v1")
public interface PoetryUserFavoriteApplicationClient extends PoetryUserFavoriteApplicationService {
}