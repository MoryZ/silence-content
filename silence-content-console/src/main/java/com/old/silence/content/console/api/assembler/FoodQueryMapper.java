package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.FoodQuery;

import com.old.silence.content.console.dto.FoodConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface FoodQueryMapper extends Converter<FoodConsoleQuery, FoodQuery> {


    @Override
    FoodQuery convert(FoodConsoleQuery source);
}
