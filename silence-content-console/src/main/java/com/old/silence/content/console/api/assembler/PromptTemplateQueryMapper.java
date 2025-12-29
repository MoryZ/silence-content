package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PromptTemplateQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PromptTemplateConsoleQuery;

/**
 * PromptTemplateCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PromptTemplateQueryMapper extends Converter<PromptTemplateConsoleQuery, PromptTemplateQuery> {

    @Override
    PromptTemplateQuery convert(PromptTemplateConsoleQuery query);
}