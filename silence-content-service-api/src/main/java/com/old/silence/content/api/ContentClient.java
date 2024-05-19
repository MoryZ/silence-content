package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author MurrayZhang
 * @description
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "content", path = "/api/v1")
public interface ContentClient extends ContentService{
}
