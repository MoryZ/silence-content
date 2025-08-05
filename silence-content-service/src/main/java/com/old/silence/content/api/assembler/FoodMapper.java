package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.FoodCommand;
import com.old.silence.content.domain.model.Food;

/**
 * @author moryzang
 */

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface FoodMapper extends Converter<FoodCommand, Food> {

    @Override
    Food convert(FoodCommand command);

}