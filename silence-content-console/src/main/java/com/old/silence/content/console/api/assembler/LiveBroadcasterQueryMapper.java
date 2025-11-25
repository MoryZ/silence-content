package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.LiveBroadcasterConsoleQuery;
import com.old.silence.content.api.dto.LiveBroadcasterQuery;

/**
* LiveBroadcasterCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface LiveBroadcasterQueryMapper extends Converter<LiveBroadcasterConsoleQuery, LiveBroadcasterQuery>{

    @Override
    LiveBroadcasterQuery convert(LiveBroadcasterConsoleQuery query);
}