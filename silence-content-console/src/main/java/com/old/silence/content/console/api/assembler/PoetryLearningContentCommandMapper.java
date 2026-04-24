package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryLearningContentCommand;

import com.old.silence.content.console.dto.PoetryLearningContentConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryLearningContent映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryLearningContentCommandMapper extends Converter<PoetryLearningContentConsoleCommand, PoetryLearningContentCommand> {

}