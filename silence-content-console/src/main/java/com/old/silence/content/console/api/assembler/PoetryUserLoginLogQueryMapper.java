package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryUserLoginLogConsoleQuery;
import com.old.silence.content.api.dto.PoetryUserLoginLogQuery;

/**
* PoetryUserLoginLogCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserLoginLogQueryMapper extends Converter<PoetryUserLoginLogConsoleQuery, PoetryUserLoginLogQuery>{

    @Override
    PoetryUserLoginLogQuery convert(PoetryUserLoginLogConsoleQuery query);
}