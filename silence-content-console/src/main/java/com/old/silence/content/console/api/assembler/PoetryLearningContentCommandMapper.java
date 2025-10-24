package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryLearningContentCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryLearningContentConsoleCommand;

/**
 * PoetryLearningContent映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryLearningContentCommandMapper extends Converter<PoetryLearningContentConsoleCommand, PoetryLearningContentCommand> {

}