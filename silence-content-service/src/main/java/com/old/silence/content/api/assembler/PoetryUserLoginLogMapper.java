package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PoetryUserLoginLogCommand;
import com.old.silence.content.domain.model.PoetryUserLoginLog;

/**
 * PoetryUser映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserLoginLogMapper extends Converter<PoetryUserLoginLogCommand, PoetryUserLoginLog> {

}