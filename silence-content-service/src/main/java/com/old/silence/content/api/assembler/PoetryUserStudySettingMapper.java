package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserStudySettingCommand;
import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserStudySetting映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserStudySettingMapper extends Converter<PoetryUserStudySettingCommand, PoetryUserStudySetting> {

    @Override
    PoetryUserStudySetting convert(PoetryUserStudySettingCommand command);
}