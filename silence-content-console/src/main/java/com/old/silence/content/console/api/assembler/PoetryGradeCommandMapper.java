package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryGradeCommand;

import com.old.silence.content.console.dto.PoetryGradeConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryGrade映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryGradeCommandMapper extends Converter<PoetryGradeConsoleCommand, PoetryGradeCommand> {


}