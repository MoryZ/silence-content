package ${packageName};

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${basePackage}.api.assembler.${className}Mapper;
import ${basePackage}.api.dto.${className}Command;
import ${basePackage}.api.dto.${className}Query;
import ${basePackage}.domain.model.${className};
import ${basePackage}.domain.repository.${className}Repository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* ${className}资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class ${className}Resource implements ${className}Service {
    private final ${className}Repository ${className?uncap_first}Repository;
    private final ${className}Mapper ${className?uncap_first}Mapper;

    public ${className}Resource(${className}Repository ${className?uncap_first}Repository,
                                ${className}Mapper ${className?uncap_first}Mapper) {
        this.${className?uncap_first}Repository = ${className?uncap_first}Repository;
        this.${className?uncap_first}Mapper = ${className?uncap_first}Mapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return ${className?uncap_first}Repository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(${className}Query query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, ${className}.class);
        return ${className?uncap_first}Repository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(${className}Command command) {
        var ${className?uncap_first} = ${className?uncap_first}Mapper.convert(command);
        ${className?uncap_first}Repository.create(${className?uncap_first});
                        return ${className?uncap_first}.getId();
                        }

    @Override
    public void update(BigInteger id, ${className}Command command) {
        var ${className?uncap_first} = ${className?uncap_first}Mapper.convert(command);
        ${className?uncap_first}.setId(id);
        validateModifyingResult(${className?uncap_first}Repository.update(${className?uncap_first}));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(${className?uncap_first}Repository.deleteById(id));
    }
}