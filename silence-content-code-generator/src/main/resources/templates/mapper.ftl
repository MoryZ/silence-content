// mapper.ftl
package ${packageName};

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ${basePackage}.api.dto.${className}Command;
import ${basePackage}.domain.model.${className};

/**
* ${className}映射器
*/
@Mapper(componentModel = "spring")
public interface ${className}Mapper extends Converter&lt;${className}Command, ${className}>{

        @Override
        ${className} convert(${className}Command command);
}