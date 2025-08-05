package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.OrderFoodCommand;
import com.old.silence.content.domain.model.OrderFood;

/**
 * @author moryzang
 */

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface OrderFoodMapper extends Converter<OrderFoodCommand, OrderFood> {

    @Override
    OrderFood convert(OrderFoodCommand command);

}