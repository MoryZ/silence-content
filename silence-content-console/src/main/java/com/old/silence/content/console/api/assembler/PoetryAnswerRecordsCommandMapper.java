package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryAnswerRecordsConsoleCommand;
import com.old.silence.content.api.dto.PoetryAnswerRecordsCommand;

/**
* PoetryAnswerRecordsCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryAnswerRecordsCommandMapper extends Converter<PoetryAnswerRecordsConsoleCommand, PoetryAnswerRecordsCommand>{

    @Override
    PoetryAnswerRecordsCommand convert(PoetryAnswerRecordsConsoleCommand command);
}