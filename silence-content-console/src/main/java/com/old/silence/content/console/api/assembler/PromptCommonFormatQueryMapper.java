package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PromptCommonFormatQuery;

import com.old.silence.content.console.dto.PromptCommonFormatConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PromptCommonFormatCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PromptCommonFormatQueryMapper extends Converter<PromptCommonFormatConsoleQuery, PromptCommonFormatQuery> {

    @Override
    PromptCommonFormatQuery convert(PromptCommonFormatConsoleQuery query);
}