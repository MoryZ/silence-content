package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.LiveRoomConsoleQuery;
import com.old.silence.content.api.dto.LiveRoomQuery;

/**
* LiveRoomCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface LiveRoomQueryMapper extends Converter<LiveRoomConsoleQuery, LiveRoomQuery>{

    @Override
    LiveRoomQuery convert(LiveRoomConsoleQuery query);
}