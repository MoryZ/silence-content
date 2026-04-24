package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryDailyStudyPlanQuery;

import com.old.silence.content.console.dto.PoetryDailyStudyPlanConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryDailyStudyPlan映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryDailyStudyPlanQueryMapper extends Converter<PoetryDailyStudyPlanConsoleQuery, PoetryDailyStudyPlanQuery> {


}