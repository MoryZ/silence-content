package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryDailyStudyPlanQuery;
import com.old.silence.content.application.api.dto.PoetryDailyStudyPlanApplicationQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryDailyStudyPlan映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryDailyStudyPlanApplicationQueryMapper extends Converter<PoetryDailyStudyPlanApplicationQuery, PoetryDailyStudyPlanQuery> {

    @Override
    PoetryDailyStudyPlanQuery convert(PoetryDailyStudyPlanApplicationQuery query);
}