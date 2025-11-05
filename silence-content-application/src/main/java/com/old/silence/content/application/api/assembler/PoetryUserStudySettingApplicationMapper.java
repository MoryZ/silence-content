package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserStudySettingCommand;
import com.old.silence.content.application.api.dto.PoetryUserStudySettingApplicationCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserStudySetting映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserStudySettingApplicationMapper extends Converter<PoetryUserStudySettingApplicationCommand, PoetryUserStudySettingCommand> {

    @Override
    PoetryUserStudySettingCommand convert(PoetryUserStudySettingApplicationCommand command);
}