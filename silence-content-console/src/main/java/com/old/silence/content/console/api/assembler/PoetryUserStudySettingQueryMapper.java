package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserStudySettingQuery;

import com.old.silence.content.console.dto.PoetryUserStudySettingConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserStudySetting映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserStudySettingQueryMapper extends Converter<PoetryUserStudySettingConsoleQuery, PoetryUserStudySettingQuery> {


}