package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PromptTemplateCommand;

import com.old.silence.content.console.dto.PromptTemplateConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PromptTemplateCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PromptTemplateCommandMapper extends Converter<PromptTemplateConsoleCommand, PromptTemplateCommand> {

    @Override
    PromptTemplateCommand convert(PromptTemplateConsoleCommand command);
}