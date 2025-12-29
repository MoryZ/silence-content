package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeApiDocumentCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.CodeApiDocumentConsoleCommand;

/**
 * CodeApiDocumentCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeApiDocumentCommandMapper extends Converter<CodeApiDocumentConsoleCommand, CodeApiDocumentCommand> {

    @Override
    CodeApiDocumentCommand convert(CodeApiDocumentConsoleCommand command);
}