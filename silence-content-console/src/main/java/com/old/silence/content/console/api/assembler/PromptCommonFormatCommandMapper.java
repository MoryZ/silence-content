package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PromptCommonFormatCommand;

import com.old.silence.content.console.dto.PromptCommonFormatConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PromptCommonFormatCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PromptCommonFormatCommandMapper extends Converter<PromptCommonFormatConsoleCommand, PromptCommonFormatCommand> {

    @Override
    @Mapping(target = "active", constant = "true")
    PromptCommonFormatCommand convert(PromptCommonFormatConsoleCommand command);
}