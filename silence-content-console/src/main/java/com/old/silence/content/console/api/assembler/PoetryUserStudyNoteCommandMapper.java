package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserStudyNoteCommand;

import com.old.silence.content.console.dto.PoetryUserStudyNoteConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserStudyNote映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserStudyNoteCommandMapper extends Converter<PoetryUserStudyNoteConsoleCommand, PoetryUserStudyNoteCommand> {


}