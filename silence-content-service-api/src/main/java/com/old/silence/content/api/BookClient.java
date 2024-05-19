package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author MurrayZhang
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "book", path = "/api/v1")
public interface BookClient extends BookService{
}
