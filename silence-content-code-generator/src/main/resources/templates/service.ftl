package ${packageName};

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import ${basePackage}.api.dto.${className}Command;
import ${basePackage}.api.dto.${className}Query;
import ${basePackage}.api.vo.${className}View;
import ${basePackage}.web.bind.annotation.PostJsonMapping;
import ${basePackage}.web.bind.annotation.PutJsonMapping;
import ${basePackage}.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
* ${className}服务接口
*/
interface ${className}Service {

        @GetMapping(value = "/${className?uncap_first}s/{id}")
        <T> Optional<T>findById(@PathVariable BigInteger id, @ProjectedPayloadType(${className}View.class) Class<T> projectionType);

        @GetMapping(value = "/${className?uncap_first}s", params = {"pageNo", "pageSize"})
        <T> Page<T> query(@Validated @SpringQueryMap ${className}Query query, Pageable pageable,
                            @ProjectedPayloadType(${className}View.class) Class<T> projectionType);

        @PostJsonMapping("/${className?uncap_first}s")
        BigInteger create(@RequestBody @Validated ${className}Command command);

        @PutJsonMapping(value = "/${className?uncap_first}s/{id}")
        void update(@PathVariable BigInteger id, @RequestBody @Validated ${className}Command command);

        @DeleteMapping("/${className?uncap_first}s/{id}")
        void deleteById(@PathVariable BigInteger id);
}