package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.LiveRoomCommand;
import com.old.silence.content.domain.model.LiveRoom;

/**
* LiveRoom映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface LiveRoomMapper extends Converter<LiveRoomCommand, LiveRoom>{

    @Override
    LiveRoom convert(LiveRoomCommand command);
}