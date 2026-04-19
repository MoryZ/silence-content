package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "event-game-reward-item", path = "/api/v1")
public interface EventGameRewardItemDomainClient extends EventGameRewardItemService {
}
