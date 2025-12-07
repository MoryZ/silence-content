package ${packageName};

import org.springframework.cloud.openfeign.FeignClient;

/**
* ${className}Feign客户端
*/
@FeignClient(name = "${applicationName!"应用名"}", contextId = "${contextId}", path = "/api/v1")
public interface ${className}Client extends ${className}Service {
}