package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryQuizQuestionsQuery;
import com.old.silence.content.application.api.dto.PoetryQuizQuestionsApplicationQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
* PoetryQuizQuestions映射器
*/
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryQuizQuestionsApplicationQueryMapper extends Converter<PoetryQuizQuestionsApplicationQuery, PoetryQuizQuestionsQuery>{


}