package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.OrderFoodCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.OrderFoodConsoleCommand;

/**
 * @author MurrayZhang
 * @Description
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface OrderFoodCommandMapper extends Converter<OrderFoodConsoleCommand, OrderFoodCommand> {


    @Override
    OrderFoodCommand convert(OrderFoodConsoleCommand source);
}
