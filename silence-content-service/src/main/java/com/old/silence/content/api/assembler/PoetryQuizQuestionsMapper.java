package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryQuizQuestionsCommand;
import com.old.silence.content.domain.model.poetry.PoetryQuizQuestions;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryQuizQuestions映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryQuizQuestionsMapper extends Converter<PoetryQuizQuestionsCommand, PoetryQuizQuestions> {

    @Override
    PoetryQuizQuestions convert(PoetryQuizQuestionsCommand command);

    PoetryQuizQuestionsCommand convertCommand(PoetryQuizQuestions poetryQuizQuestions);
}