package ${packageName};

import org.springframework.cloud.openfeign.FeignClient;

/**
* ${className}Feign客户端
*/
@FeignClient(name = "${applicationName}", contextId = "${className?uncap_first}", path = "/api/v1")
public interface ${className}Client extends ${className}Service {
}