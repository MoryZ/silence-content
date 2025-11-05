package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserStudyNoteCommand;
import com.old.silence.content.application.api.dto.PoetryUserStudyNoteApplicationCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserStudyNote映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserStudyNoteApplicationMapper extends Converter<PoetryUserStudyNoteApplicationCommand, PoetryUserStudyNoteCommand> {

    @Override
    PoetryUserStudyNoteCommand convert(PoetryUserStudyNoteApplicationCommand command);
}