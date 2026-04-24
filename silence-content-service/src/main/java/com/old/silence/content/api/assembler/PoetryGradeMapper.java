package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryGradeCommand;
import com.old.silence.content.domain.model.poetry.PoetryGrade;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryGrade映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryGradeMapper extends Converter<PoetryGradeCommand, PoetryGrade> {

    @Override
    PoetryGrade convert(PoetryGradeCommand command);
}