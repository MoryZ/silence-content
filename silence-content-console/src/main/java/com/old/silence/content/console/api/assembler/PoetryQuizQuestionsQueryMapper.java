package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryQuizQuestionsQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryQuizQuestionsConsoleQuery;

/**
 * PoetryQuizQuestionsCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryQuizQuestionsQueryMapper extends Converter<PoetryQuizQuestionsConsoleQuery, PoetryQuizQuestionsQuery> {

    @Override
    PoetryQuizQuestionsQuery convert(PoetryQuizQuestionsConsoleQuery query);
}