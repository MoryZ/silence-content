package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeFileTemplateCommand;

import com.old.silence.content.console.dto.CodeFileTemplateConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;


/**
 * FreemarkerTemplatesCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeFileTemplateCommandMapper extends Converter<CodeFileTemplateConsoleCommand, CodeFileTemplateCommand> {

    @Override
    CodeFileTemplateCommand convert(CodeFileTemplateConsoleCommand command);
}