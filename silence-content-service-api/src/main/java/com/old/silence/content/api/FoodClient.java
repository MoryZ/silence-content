package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author MurrayZhang
 * @Description
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "food", path = "/api/v1")
public interface FoodClient extends FoodService {
}
