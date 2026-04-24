package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryLearningContentCommand;
import com.old.silence.content.domain.model.poetry.PoetryLearningContent;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryLearningContent映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryLearningContentMapper extends Converter<PoetryLearningContentCommand, PoetryLearningContent> {

    @Override
    PoetryLearningContent convert(PoetryLearningContentCommand command);
}