package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryCategoryCommand;
import com.old.silence.content.domain.model.poetry.PoetryCategory;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryCategory映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryCategoryMapper extends Converter<PoetryCategoryCommand, PoetryCategory> {

    @Override
    PoetryCategory convert(PoetryCategoryCommand command);
}