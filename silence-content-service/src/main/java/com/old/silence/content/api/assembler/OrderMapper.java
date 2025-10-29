package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.OrderCommand;
import com.old.silence.content.domain.model.Order;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author moryzang
 */

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface OrderMapper extends Converter<OrderCommand, Order> {

    @Override
    @Mapping(target = "price", expression = "java(computed(command))")
    Order convert(OrderCommand command);


    default BigDecimal computed(OrderCommand command) {
        var sumPrice = command.getOrderFoods().stream().map(orderFoodCommand -> orderFoodCommand.getPrice().multiply(BigDecimal.valueOf(orderFoodCommand.getNumber())))
                .reduce(BigDecimal::add).orElseThrow(ArithmeticException::new);
        return sumPrice.subtract(Optional.ofNullable(command.getPromotion()).orElse(BigDecimal.ZERO));
    }

}