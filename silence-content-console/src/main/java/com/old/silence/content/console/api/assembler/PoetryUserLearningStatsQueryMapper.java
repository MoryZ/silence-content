package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserLearningStatsQuery;

import com.old.silence.content.console.dto.PoetryUserLearningStatsConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserLearningStats映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserLearningStatsQueryMapper extends Converter<PoetryUserLearningStatsConsoleQuery, PoetryUserLearningStatsQuery> {


}