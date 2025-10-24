package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryGradeCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryGradeConsoleCommand;

/**
 * PoetryGrade映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryGradeCommandMapper extends Converter<PoetryGradeConsoleCommand, PoetryGradeCommand> {


}