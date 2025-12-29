package com.old.silence.content.console.api.assembler;


import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeFileSpecCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.CodeFileSpecConsoleCommand;


/**
 * CodeFileSpecCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeFileSpecCommandMapper extends Converter<CodeFileSpecConsoleCommand, CodeFileSpecCommand> {

    @Override
    CodeFileSpecCommand convert(CodeFileSpecConsoleCommand command);
}