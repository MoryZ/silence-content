package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
* LiveRoomOrganizationFeign客户端
*/
@FeignClient(name = "silence-content-service", contextId = "live-room-organization", path = "/api/v1")
public interface LiveRoomOrganizationClient extends LiveRoomOrganizationService {
}