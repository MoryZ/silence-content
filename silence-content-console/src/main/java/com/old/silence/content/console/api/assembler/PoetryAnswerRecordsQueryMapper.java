package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryAnswerRecordsQuery;

import com.old.silence.content.console.dto.PoetryAnswerRecordsConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryAnswerRecordsCommand映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryAnswerRecordsQueryMapper extends Converter<PoetryAnswerRecordsConsoleQuery, PoetryAnswerRecordsQuery> {

    @Override
    PoetryAnswerRecordsQuery convert(PoetryAnswerRecordsConsoleQuery query);
}