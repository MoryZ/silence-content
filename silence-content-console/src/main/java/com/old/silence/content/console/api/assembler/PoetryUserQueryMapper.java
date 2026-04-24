package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserQuery;

import com.old.silence.content.console.dto.PoetryUserConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUser映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserQueryMapper extends Converter<PoetryUserConsoleQuery, PoetryUserQuery> {


}