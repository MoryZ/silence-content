package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.LiveRoomOrganizationConsoleQuery;
import com.old.silence.content.api.dto.LiveRoomOrganizationQuery;

/**
* LiveRoomOrganizationCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface LiveRoomOrganizationQueryMapper extends Converter<LiveRoomOrganizationConsoleQuery, LiveRoomOrganizationQuery>{

    @Override
    LiveRoomOrganizationQuery convert(LiveRoomOrganizationConsoleQuery query);
}