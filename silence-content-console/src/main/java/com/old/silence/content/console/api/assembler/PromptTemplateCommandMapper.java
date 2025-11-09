package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PromptTemplateConsoleCommand;
import com.old.silence.content.api.dto.PromptTemplateCommand;

/**
* PromptTemplateCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PromptTemplateCommandMapper extends Converter<PromptTemplateConsoleCommand, PromptTemplateCommand>{

    @Override
    PromptTemplateCommand convert(PromptTemplateConsoleCommand command);
}