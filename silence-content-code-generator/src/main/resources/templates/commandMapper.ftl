package ${packageName};

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import ${basePackage}.console.dto.${className}ConsoleCommand;
import ${basePackage}.api.dto.${className}Command;

/**
* ${className}Command映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ${className}CommandMapper extends Converter<${className}ConsoleCommand, ${className}Command>{

    @Override
    ${className}Command convert(${className}ConsoleCommand command);
}