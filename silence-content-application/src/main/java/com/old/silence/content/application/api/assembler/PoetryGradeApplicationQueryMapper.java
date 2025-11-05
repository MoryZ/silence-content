package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryGradeQuery;
import com.old.silence.content.application.api.dto.PoetryGradeApplicationQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryGrade映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryGradeApplicationQueryMapper extends Converter<PoetryGradeApplicationQuery, PoetryGradeQuery> {

    @Override
    PoetryGradeQuery convert(PoetryGradeApplicationQuery query);
}