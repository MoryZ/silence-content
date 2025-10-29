package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PoetryUserStudyNoteCommand;
import com.old.silence.content.domain.model.PoetryUserStudyNote;

/**
 * PoetryUserStudyNote映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserStudyNoteMapper extends Converter<PoetryUserStudyNoteCommand, PoetryUserStudyNote> {

    @Override
    PoetryUserStudyNote convert(PoetryUserStudyNoteCommand command);
}