package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryAnswerRecordsQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryAnswerRecordsConsoleQuery;

/**
 * PoetryAnswerRecordsCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryAnswerRecordsQueryMapper extends Converter<PoetryAnswerRecordsConsoleQuery, PoetryAnswerRecordsQuery> {

    @Override
    PoetryAnswerRecordsQuery convert(PoetryAnswerRecordsConsoleQuery query);
}