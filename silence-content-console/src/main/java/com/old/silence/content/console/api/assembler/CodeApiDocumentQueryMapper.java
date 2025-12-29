package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.CodeApiDocumentConsoleQuery;
import com.old.silence.content.api.dto.CodeApiDocumentQuery;

/**
* CodeApiDocumentCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeApiDocumentQueryMapper extends Converter<CodeApiDocumentConsoleQuery, CodeApiDocumentQuery>{

    @Override
    CodeApiDocumentQuery convert(CodeApiDocumentConsoleQuery query);
}