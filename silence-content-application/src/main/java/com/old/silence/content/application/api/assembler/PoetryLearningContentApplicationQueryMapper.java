package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryLearningContentQuery;
import com.old.silence.content.application.api.dto.PoetryLearningContentApplicationQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryLearningContent映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryLearningContentApplicationQueryMapper extends Converter<PoetryLearningContentApplicationQuery,
        PoetryLearningContentQuery> {

    @Override
    PoetryLearningContentQuery convert(PoetryLearningContentApplicationQuery query);
}