package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PoetryAnswerRecordsCommand;
import com.old.silence.content.domain.model.poetry.PoetryAnswerRecords;

/**
* PoetryAnswerRecords映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryAnswerRecordsMapper extends Converter<PoetryAnswerRecordsCommand, PoetryAnswerRecords>{

        @Override
        PoetryAnswerRecords convert(PoetryAnswerRecordsCommand command);
}