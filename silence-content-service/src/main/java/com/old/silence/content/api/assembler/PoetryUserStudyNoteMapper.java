package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserStudyNoteCommand;
import com.old.silence.content.domain.model.poetry.PoetryUserStudyNote;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserStudyNote映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserStudyNoteMapper extends Converter<PoetryUserStudyNoteCommand, PoetryUserStudyNote> {

    @Override
    PoetryUserStudyNote convert(PoetryUserStudyNoteCommand command);
}