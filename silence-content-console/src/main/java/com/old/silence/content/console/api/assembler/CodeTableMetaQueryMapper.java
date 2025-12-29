package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.CodeTableMetaConsoleQuery;
import com.old.silence.content.api.dto.CodeTableMetaQuery;

/**
* CodeTableMetaCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeTableMetaQueryMapper extends Converter<CodeTableMetaConsoleQuery, CodeTableMetaQuery>{

    @Override
    CodeTableMetaQuery convert(CodeTableMetaConsoleQuery query);
}