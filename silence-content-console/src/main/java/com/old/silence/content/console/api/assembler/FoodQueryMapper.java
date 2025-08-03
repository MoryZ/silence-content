package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.FoodQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.FoodConsoleQuery;

/**
 * @author moryzang
 * @Description
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface FoodQueryMapper extends Converter<FoodConsoleQuery, FoodQuery> {


    @Override
    FoodQuery convert(FoodConsoleQuery source);
}
