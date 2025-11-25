package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.LiveRoomOrganizationConsoleCommand;
import com.old.silence.content.api.dto.LiveRoomOrganizationCommand;

/**
* LiveRoomOrganizationCommand映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface LiveRoomOrganizationCommandMapper extends Converter<LiveRoomOrganizationConsoleCommand, LiveRoomOrganizationCommand>{

    @Override
    LiveRoomOrganizationCommand convert(LiveRoomOrganizationConsoleCommand command);
}