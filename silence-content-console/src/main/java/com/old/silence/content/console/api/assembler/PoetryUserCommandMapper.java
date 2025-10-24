package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryUserConsoleCommand;

/**
 * PoetryUser映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserCommandMapper extends Converter<PoetryUserConsoleCommand, PoetryUserCommand> {


}