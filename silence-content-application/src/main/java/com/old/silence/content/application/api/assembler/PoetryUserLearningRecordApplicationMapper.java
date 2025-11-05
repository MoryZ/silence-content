package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserLearningRecordCommand;
import com.old.silence.content.application.api.dto.PoetryUserLearningRecordApplicationCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserLearningRecord映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserLearningRecordApplicationMapper extends Converter<PoetryUserLearningRecordApplicationCommand, PoetryUserLearningRecordCommand> {

    @Override
    PoetryUserLearningRecordCommand convert(PoetryUserLearningRecordApplicationCommand command);
}