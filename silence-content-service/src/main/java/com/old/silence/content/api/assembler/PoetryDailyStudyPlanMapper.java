package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryDailyStudyPlanCommand;
import com.old.silence.content.domain.model.poetry.PoetryDailyStudyPlan;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryDailyStudyPlan映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryDailyStudyPlanMapper extends Converter<PoetryDailyStudyPlanCommand, PoetryDailyStudyPlan> {

    @Override
    PoetryDailyStudyPlan convert(PoetryDailyStudyPlanCommand command);
}