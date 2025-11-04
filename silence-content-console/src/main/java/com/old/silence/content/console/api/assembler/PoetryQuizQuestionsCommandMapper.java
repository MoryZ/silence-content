package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryQuizQuestionsConsoleCommand;
import com.old.silence.content.api.dto.PoetryQuizQuestionsCommand;

/**
* PoetryQuizQuestionsCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryQuizQuestionsCommandMapper extends Converter<PoetryQuizQuestionsConsoleCommand, PoetryQuizQuestionsCommand>{

    @Override
    PoetryQuizQuestionsCommand convert(PoetryQuizQuestionsConsoleCommand command);
}