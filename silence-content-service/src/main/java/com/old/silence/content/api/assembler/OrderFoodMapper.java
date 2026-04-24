package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.OrderFoodCommand;
import com.old.silence.content.domain.model.takeout.OrderFood;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */

@Mapper(uses = MapStructSpringConfig.class)
public interface OrderFoodMapper extends Converter<OrderFoodCommand, OrderFood> {

    @Override
    OrderFood convert(OrderFoodCommand command);

}