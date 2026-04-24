package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeApiDocumentQuery;

import com.old.silence.content.console.dto.CodeApiDocumentConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * CodeApiDocumentCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeApiDocumentQueryMapper extends Converter<CodeApiDocumentConsoleQuery, CodeApiDocumentQuery> {

    @Override
    CodeApiDocumentQuery convert(CodeApiDocumentConsoleQuery query);
}