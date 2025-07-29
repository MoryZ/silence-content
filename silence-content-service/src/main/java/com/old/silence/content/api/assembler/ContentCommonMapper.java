package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.old.silence.content.api.assembler.support.ContentAccessMapper;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.ContentCommonCommand;
import com.old.silence.content.api.dto.ContentLiveCommand;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.model.ContentLive;
import com.old.silence.content.domain.model.support.ContentCommon;

/**
 * @author MurrayZhang
 */

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentCommonMapper extends ContentAccessMapper<ContentCommonCommand, ContentCommon> {

    @Override
    @Mapping(target = "content", source = "command")
    ContentCommon convert(ContentCommonCommand command);

    @Mapping(target = "contentContentTags", expression = "java(toContentContentTags(command.getTagIds()))")
    Content toContent(ContentCommonCommand command);

}