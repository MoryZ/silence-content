package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.LiveRoomConsoleCommand;
import com.old.silence.content.api.dto.LiveRoomCommand;

/**
* LiveRoomCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface LiveRoomCommandMapper extends Converter<LiveRoomConsoleCommand, LiveRoomCommand>{

    @Override
    @Mapping(target = "deleted", constant = "false")
    LiveRoomCommand convert(LiveRoomConsoleCommand command);
}