package com.old.silence.content.console.api.assembler;


import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.dto.CodeFileSpecQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.CodeFileSpecConsoleQuery;


/**
* CodeFileSpecCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeFileSpecQueryMapper extends Converter<CodeFileSpecConsoleQuery, CodeFileSpecQuery>{

    @Override
    CodeFileSpecQuery convert(CodeFileSpecConsoleQuery query);
}