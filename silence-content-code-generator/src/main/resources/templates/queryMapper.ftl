package ${packageName};

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import ${basePackage}.console.dto.${className}ConsoleQuery;
import ${basePackage}.api.dto.${className}Query;

/**
* ${className}Command映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ${className}QueryMapper extends Converter<${className}ConsoleQuery, ${className}Query>{

    @Override
    ${className}Query convert(${className}ConsoleQuery query);
}