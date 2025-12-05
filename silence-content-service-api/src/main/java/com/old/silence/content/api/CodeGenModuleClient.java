package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author moryzang
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "code-gen-module", path = "/api/v1")
public interface CodeGenModuleClient extends CodeGenModuleService {
}
