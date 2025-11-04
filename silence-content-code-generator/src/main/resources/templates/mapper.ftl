package ${packageName};

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import ${basePackage}.api.dto.${className}Command;
import ${basePackage}.domain.model.${className};

/**
* ${className}映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ${className}Mapper extends Converter<${className}Command, ${className}>{

        @Override
        ${className} convert(${className}Command command);
}