package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PoetryUserStudySettingCommand;
import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;

/**
 * PoetryUserStudySetting映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserStudySettingMapper extends Converter<PoetryUserStudySettingCommand, PoetryUserStudySetting> {

    @Override
    PoetryUserStudySetting convert(PoetryUserStudySettingCommand command);
}