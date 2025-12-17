package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.UserInteractionLogCommand;
import com.old.silence.content.domain.model.poetry.UserInteractionLog;

/**
 * UserInteractionLog映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface UserInteractionLogMapper extends Converter<UserInteractionLogCommand, UserInteractionLog> {

    @Override
    UserInteractionLog convert(UserInteractionLogCommand command);
}