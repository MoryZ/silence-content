package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.CodeTableMetaCommand;
import com.old.silence.content.domain.model.CodeTableMeta;

/**
* CodeTableMeta映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeTableMetaMapper extends Converter<CodeTableMetaCommand, CodeTableMeta>{

    @Override
    CodeTableMeta convert(CodeTableMetaCommand command);
}