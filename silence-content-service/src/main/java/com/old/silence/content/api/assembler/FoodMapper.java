package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.FoodCommand;
import com.old.silence.content.domain.model.takeout.Food;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */

@Mapper(uses = MapStructSpringConfig.class)
public interface FoodMapper extends Converter<FoodCommand, Food> {

    @Override
    Food convert(FoodCommand command);

}