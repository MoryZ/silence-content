package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.LiveBroadcasterConsoleCommand;
import com.old.silence.content.api.dto.LiveBroadcasterCommand;

/**
* LiveBroadcasterCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface LiveBroadcasterCommandMapper extends Converter<LiveBroadcasterConsoleCommand, LiveBroadcasterCommand>{

    @Override
    @Mapping(target = "deleted", constant = "false")
    LiveBroadcasterCommand convert(LiveBroadcasterConsoleCommand command);
}