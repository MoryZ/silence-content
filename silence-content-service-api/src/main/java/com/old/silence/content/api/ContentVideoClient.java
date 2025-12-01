package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author moryzang
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "content-video", path = "/api/v1")
public interface ContentVideoClient extends ContentVideoService {
}
