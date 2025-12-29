package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * BookFeign客户端
 */
@FeignClient(name = "silence-content-service", contextId = "book", path = "/api/v1")
public interface BookClient extends BookService {
}