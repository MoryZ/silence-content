package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryCategoryQuery;

import com.old.silence.content.console.dto.PoetryCategoryConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryCategory映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryCategoryQueryMapper extends Converter<PoetryCategoryConsoleQuery, PoetryCategoryQuery> {


}