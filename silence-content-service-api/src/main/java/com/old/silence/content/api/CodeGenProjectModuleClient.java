package com.old.silence.content.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author moryzang
 */
@FeignClient(name = ContentContextUtils.APPLICATION_NAME, contextId = "code-gen-project-module", path = "/api/v1")
public interface CodeGenProjectModuleClient extends CodeGenProjectModuleService {
}
