package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeApiDocumentCommand;

import com.old.silence.content.console.dto.CodeApiDocumentConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * CodeApiDocumentCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeApiDocumentCommandMapper extends Converter<CodeApiDocumentConsoleCommand, CodeApiDocumentCommand> {

    @Override
    CodeApiDocumentCommand convert(CodeApiDocumentConsoleCommand command);
}