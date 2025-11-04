package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryUserLoginLogConsoleCommand;
import com.old.silence.content.api.dto.PoetryUserLoginLogCommand;

/**
* PoetryUserLoginLogCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserLoginLogCommandMapper extends Converter<PoetryUserLoginLogConsoleCommand, PoetryUserLoginLogCommand>{

    @Override
    PoetryUserLoginLogCommand convert(PoetryUserLoginLogConsoleCommand command);
}