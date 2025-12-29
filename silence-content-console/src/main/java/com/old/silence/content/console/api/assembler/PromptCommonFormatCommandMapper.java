package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PromptCommonFormatCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PromptCommonFormatConsoleCommand;

/**
 * PromptCommonFormatCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PromptCommonFormatCommandMapper extends Converter<PromptCommonFormatConsoleCommand, PromptCommonFormatCommand> {

    @Override
    @Mapping(target = "active", constant = "true")
    PromptCommonFormatCommand convert(PromptCommonFormatConsoleCommand command);
}