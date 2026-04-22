package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "marketing-event", path = "/api/v1")
public interface MarketingEventDomainClient extends MarketingEventService {
}
