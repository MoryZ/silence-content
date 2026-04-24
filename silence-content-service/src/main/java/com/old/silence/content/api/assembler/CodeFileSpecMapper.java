package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeFileSpecCommand;
import com.old.silence.content.domain.model.codegen.CodeFileSpec;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * CodeFileSpec映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeFileSpecMapper extends Converter<CodeFileSpecCommand, CodeFileSpec> {

    @Override
    CodeFileSpec convert(CodeFileSpecCommand command);
}