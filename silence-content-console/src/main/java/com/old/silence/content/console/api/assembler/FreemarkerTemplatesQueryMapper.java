package com.old.silence.content.console.api.assembler;

import java.lang.Override;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.FreemarkerTemplatesQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.FreemarkerTemplatesConsoleQuery;


/**
 * FreemarkerTemplatesCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface FreemarkerTemplatesQueryMapper extends Converter<FreemarkerTemplatesConsoleQuery, FreemarkerTemplatesQuery>{

    @Override
    FreemarkerTemplatesQuery convert(FreemarkerTemplatesConsoleQuery query);
}