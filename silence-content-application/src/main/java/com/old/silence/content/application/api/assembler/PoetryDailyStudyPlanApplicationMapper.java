package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryDailyStudyPlanCommand;
import com.old.silence.content.application.api.dto.PoetryDailyStudyPlanApplicationCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryDailyStudyPlan映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryDailyStudyPlanApplicationMapper extends Converter<PoetryDailyStudyPlanApplicationCommand, PoetryDailyStudyPlanCommand> {

    @Override
    PoetryDailyStudyPlanCommand convert(PoetryDailyStudyPlanApplicationCommand command);
}