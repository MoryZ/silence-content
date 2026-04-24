package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserLearningRecordQuery;

import com.old.silence.content.console.dto.PoetryUserLearningRecordConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserLearningRecord映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserLearningRecordQueryMapper extends Converter<PoetryUserLearningRecordConsoleQuery, PoetryUserLearningRecordQuery> {


}