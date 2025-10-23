package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PoetryUserLearningRecordCommand;
import com.old.silence.content.domain.model.PoetryUserLearningRecord;

/**
* PoetryUserLearningRecord映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserLearningRecordMapper extends Converter<PoetryUserLearningRecordCommand, PoetryUserLearningRecord>{

        @Override
        PoetryUserLearningRecord convert(PoetryUserLearningRecordCommand command);
}