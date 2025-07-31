package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.OrderCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.OrderConsoleCommand;

/**
 * @author moryzang
 * @Description
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface OrderCommandMapper extends Converter<OrderConsoleCommand, OrderCommand> {


    @Override
    @Mapping(target = "promotion", defaultValue = "0")
    @Mapping(target = "status", defaultValue = "ORDERED")
    @Mapping(target = "taken", defaultValue = "false")
    @Mapping(target = "paid", defaultValue = "false")
    @Mapping(target = "userId", constant = "SYSTEM")
    OrderCommand convert(OrderConsoleCommand source);
}
