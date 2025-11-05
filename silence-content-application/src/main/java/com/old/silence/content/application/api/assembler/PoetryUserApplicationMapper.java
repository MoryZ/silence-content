package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserCommand;
import com.old.silence.content.application.api.dto.PoetryUserApplicationCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUser映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserApplicationMapper extends Converter<PoetryUserApplicationCommand, PoetryUserCommand> {

}