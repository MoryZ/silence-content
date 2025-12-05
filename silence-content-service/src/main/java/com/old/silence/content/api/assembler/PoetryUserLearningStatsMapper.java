package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PoetryUserLearningStatsCommand;
import com.old.silence.content.domain.model.poetry.PoetryUserLearningStats;

/**
 * PoetryUserLearningStats映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserLearningStatsMapper extends Converter<PoetryUserLearningStatsCommand, PoetryUserLearningStats> {

    @Override
    PoetryUserLearningStats convert(PoetryUserLearningStatsCommand command);
}