package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.UserInteractionLogCommand;
import com.old.silence.content.domain.model.poetry.UserInteractionLog;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * UserInteractionLog映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface UserInteractionLogMapper extends Converter<UserInteractionLogCommand, UserInteractionLog> {

    @Override
    UserInteractionLog convert(UserInteractionLogCommand command);
}