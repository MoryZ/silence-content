package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.LiveBroadcasterCommand;
import com.old.silence.content.domain.model.LiveBroadcaster;

/**
* LiveBroadcaster映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface LiveBroadcasterMapper extends Converter<LiveBroadcasterCommand, LiveBroadcaster>{

    @Override
    LiveBroadcaster convert(LiveBroadcasterCommand command);
}