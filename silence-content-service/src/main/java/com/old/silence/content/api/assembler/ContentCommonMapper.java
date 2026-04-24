package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.old.silence.content.api.assembler.support.ContentAccessMapper;
import com.old.silence.content.api.dto.ContentCommonCommand;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.model.support.ContentCommon;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */

@Mapper(uses = MapStructSpringConfig.class)
public interface ContentCommonMapper extends ContentAccessMapper<ContentCommonCommand, ContentCommon> {

    @Override
    @Mapping(target = "content", source = "command")
    ContentCommon convert(ContentCommonCommand command);

    @Mapping(target = "contentContentTags", expression = "java(toContentContentTags(command.getTagIds()))")
    Content toContent(ContentCommonCommand command);

}