package ${packageName};

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ${basePackage}.api.assembler.${className}CommandMapper;
import ${basePackage}.api.assembler.${className}QueryMapper;
import ${basePackage}.dto.${className}ConsoleCommand;
import ${basePackage}.dto.${className}ConsoleQuery;
import ${basePackage}.vo.${className}ConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import ${serviceApiPackage}.api.${className}Client;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
* ${className}资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class ${className}Resource {
    private final ${className}Client ${className?uncap_first}Client;
    private final ${className}CommandMapper ${className?uncap_first}CommandMapper;
    private final ${className}QueryMapper ${className?uncap_first}QueryMapper;

    public ${className}Resource(${className}Client ${className?uncap_first}Client,
                                ${className}CommandMapper ${className?uncap_first}CommandMapper,
                                ${className}QueryMapper ${className?uncap_first}QueryMapper) {
        this.${className?uncap_first}Client = ${className?uncap_first}Client;
        this.${className?uncap_first}CommandMapper = ${className?uncap_first}CommandMapper;
        this.${className?uncap_first}QueryMapper = ${className?uncap_first}QueryMapper;
    }

    @GetMapping(value = "/${apiName}/{id}")
    public ${className}ConsoleView findById(@PathVariable BigInteger id) {
        return ${className?uncap_first}Client.findById(id, ${className}ConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/${apiName}", params = {"pageNo", "pageSize"})
    public Page<${className}ConsoleView> query(${className}ConsoleQuery query, Pageable pageable) {
        var ${className?uncap_first}Query = ${className?uncap_first}QueryMapper.convert(query);
        return ${className?uncap_first}Client.query(${className?uncap_first}Query, pageable, ${className}ConsoleView.class);
    }

    @PostJsonMapping("/${apiName}")
    public BigInteger create(@RequestBody ${className}ConsoleCommand command) {
        var ${className?uncap_first}Command = ${className?uncap_first}CommandMapper.convert(command);
        return ${className?uncap_first}Client.create(${className?uncap_first}Command);
    }

    @PutJsonMapping(value = "/${apiName}/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody ${className}ConsoleCommand command) {
        var ${className?uncap_first}Command = ${className?uncap_first}CommandMapper.convert(command);
        ${className?uncap_first}Client.update(id, ${className?uncap_first}Command);
    }

    @DeleteMapping("/${apiName}/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        ${className?uncap_first}Client.deleteById(id);
    }
}