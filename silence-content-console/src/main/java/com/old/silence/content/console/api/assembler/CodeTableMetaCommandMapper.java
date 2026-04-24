package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeTableMetaCommand;

import com.old.silence.content.console.dto.CodeTableMetaConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * CodeTableMetaCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeTableMetaCommandMapper extends Converter<CodeTableMetaConsoleCommand, CodeTableMetaCommand> {

    @Override
    CodeTableMetaCommand convert(CodeTableMetaConsoleCommand command);
}