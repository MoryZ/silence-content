package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PromptCommonFormatConsoleCommand;
import com.old.silence.content.api.dto.PromptCommonFormatCommand;

/**
* PromptCommonFormatCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PromptCommonFormatCommandMapper extends Converter<PromptCommonFormatConsoleCommand, PromptCommonFormatCommand>{

    @Override
    PromptCommonFormatCommand convert(PromptCommonFormatConsoleCommand command);
}