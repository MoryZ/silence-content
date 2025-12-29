package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.CodeApiDocumentCommand;
import com.old.silence.content.domain.model.CodeApiDocument;

/**
* CodeApiDocument映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeApiDocumentMapper extends Converter<CodeApiDocumentCommand, CodeApiDocument>{

    @Override
    CodeApiDocument convert(CodeApiDocumentCommand command);
}