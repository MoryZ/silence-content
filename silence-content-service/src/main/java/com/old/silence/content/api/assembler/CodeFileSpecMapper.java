package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.CodeFileSpecCommand;
import com.old.silence.content.domain.model.codegen.CodeFileSpec;

/**
* CodeFileSpec映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeFileSpecMapper extends Converter<CodeFileSpecCommand, CodeFileSpec>{

    @Override
    CodeFileSpec convert(CodeFileSpecCommand command);
}