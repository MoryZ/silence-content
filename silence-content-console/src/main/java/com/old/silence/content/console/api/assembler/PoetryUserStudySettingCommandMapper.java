package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserStudySettingCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryUserStudySettingConsoleCommand;

/**
 * PoetryUserStudySetting映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserStudySettingCommandMapper extends Converter<PoetryUserStudySettingConsoleCommand, PoetryUserStudySettingCommand> {


}