package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* LiveBroadcasterFeign客户端
*/
@FeignClient(name = "silence-content-service", contextId = "live-broadcaster", path = "/api/v1")
public interface LiveBroadcasterClient extends LiveBroadcasterService {
}