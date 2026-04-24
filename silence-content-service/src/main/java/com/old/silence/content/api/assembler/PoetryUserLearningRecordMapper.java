package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserLearningRecordCommand;
import com.old.silence.content.domain.model.poetry.PoetryUserLearningRecord;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserLearningRecord映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserLearningRecordMapper extends Converter<PoetryUserLearningRecordCommand, PoetryUserLearningRecord> {

    @Override
    PoetryUserLearningRecord convert(PoetryUserLearningRecordCommand command);
}