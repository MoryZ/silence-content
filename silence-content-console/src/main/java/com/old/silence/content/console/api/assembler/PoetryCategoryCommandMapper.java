package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryCategoryCommand;

import com.old.silence.content.console.dto.PoetryCategoryConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryCategory映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryCategoryCommandMapper extends Converter<PoetryCategoryConsoleCommand, PoetryCategoryCommand> {

    @Override
    @Mapping(target = "enabled", constant = "true")
    PoetryCategoryCommand convert(PoetryCategoryConsoleCommand source);
}