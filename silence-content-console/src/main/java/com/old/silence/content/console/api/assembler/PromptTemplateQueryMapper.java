package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PromptTemplateQuery;

import com.old.silence.content.console.dto.PromptTemplateConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PromptTemplateCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PromptTemplateQueryMapper extends Converter<PromptTemplateConsoleQuery, PromptTemplateQuery> {

    @Override
    PromptTemplateQuery convert(PromptTemplateConsoleQuery query);
}