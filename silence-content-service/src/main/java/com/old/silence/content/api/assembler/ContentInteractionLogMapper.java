package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentInteractionLogCommand;
import com.old.silence.content.domain.model.ContentInteractionLog;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * ContentInteractionLog映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface ContentInteractionLogMapper extends Converter<ContentInteractionLogCommand, ContentInteractionLog> {

    @Override
    ContentInteractionLog convert(ContentInteractionLogCommand command);
}