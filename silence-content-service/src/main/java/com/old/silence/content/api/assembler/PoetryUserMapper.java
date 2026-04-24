package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserCommand;
import com.old.silence.content.domain.model.poetry.PoetryUser;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUser映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserMapper extends Converter<PoetryUserCommand, PoetryUser> {

}