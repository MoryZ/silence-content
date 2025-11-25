package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* LiveRoomFeign客户端
*/
@FeignClient(name = "silence-content-service", contextId = "live-room", path = "/api/v1")
public interface LiveRoomClient extends LiveRoomService {
}