package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryLearningContentQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryLearningContentConsoleQuery;

/**
 * PoetryLearningContent映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryLearningContentQueryMapper extends Converter<PoetryLearningContentConsoleQuery, PoetryLearningContentQuery> {

}