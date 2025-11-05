package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserLoginLogCommand;
import com.old.silence.content.application.api.dto.PoetryUserLoginLogApplicationCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
* PoetryUserLoginLog映射器
*/
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserLoginLogApplicationMapper extends Converter<PoetryUserLoginLogApplicationCommand, PoetryUserLoginLogCommand>{

    @Override
    PoetryUserLoginLogCommand convert(PoetryUserLoginLogApplicationCommand command);
}