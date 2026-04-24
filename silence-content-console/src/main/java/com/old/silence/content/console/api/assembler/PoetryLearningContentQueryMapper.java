package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryLearningContentQuery;

import com.old.silence.content.console.dto.PoetryLearningContentConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryLearningContent映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryLearningContentQueryMapper extends Converter<PoetryLearningContentConsoleQuery, PoetryLearningContentQuery> {

}