package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Provision 客户端
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "provision", path = "/api/v1")
public interface ProvisionClient extends ProvisionService {
}