package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryGradeQuery;

import com.old.silence.content.console.dto.PoetryGradeConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryGrade映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryGradeQueryMapper extends Converter<PoetryGradeConsoleQuery, PoetryGradeQuery> {


}