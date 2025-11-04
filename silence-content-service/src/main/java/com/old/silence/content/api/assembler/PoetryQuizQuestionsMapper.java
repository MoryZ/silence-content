package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PoetryQuizQuestionsCommand;
import com.old.silence.content.domain.model.PoetryQuizQuestions;

/**
* PoetryQuizQuestions映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryQuizQuestionsMapper extends Converter<PoetryQuizQuestionsCommand, PoetryQuizQuestions>{

        @Override
        PoetryQuizQuestions convert(PoetryQuizQuestionsCommand command);
}