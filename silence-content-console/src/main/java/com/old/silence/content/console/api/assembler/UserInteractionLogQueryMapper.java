package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.UserInteractionLogQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.UserInteractionLogConsoleQuery;

/**
 * UserInteractionLog映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface UserInteractionLogQueryMapper extends Converter<UserInteractionLogConsoleQuery, UserInteractionLogQuery> {


}