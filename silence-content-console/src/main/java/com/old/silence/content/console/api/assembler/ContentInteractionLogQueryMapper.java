package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentInteractionLogQuery;

import com.old.silence.content.console.dto.ContentInteractionLogConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * ContentInteractionLog映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface ContentInteractionLogQueryMapper extends Converter<ContentInteractionLogConsoleQuery, ContentInteractionLogQuery> {


}