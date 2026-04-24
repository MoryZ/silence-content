package com.old.silence.content.console.api.assembler;


import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeFileSpecCommand;

import com.old.silence.content.console.dto.CodeFileSpecConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;


/**
 * CodeFileSpecCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeFileSpecCommandMapper extends Converter<CodeFileSpecConsoleCommand, CodeFileSpecCommand> {

    @Override
    CodeFileSpecCommand convert(CodeFileSpecConsoleCommand command);
}