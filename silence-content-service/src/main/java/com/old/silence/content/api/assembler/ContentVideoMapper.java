package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.old.silence.content.api.assembler.support.ContentAccessMapper;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.ContentLiveCommand;
import com.old.silence.content.api.dto.ContentVideoCommand;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.model.ContentLive;
import com.old.silence.content.domain.model.ContentVideo;

/**
 * @author moryzang
 */

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentVideoMapper extends ContentAccessMapper<ContentVideoCommand, ContentVideo> {

    @Override
    @Mapping(target = "content", source = "command")
    ContentVideo convert(ContentVideoCommand command);

    @Mapping(target = "contentContentTags", expression = "java(toContentContentTags(command.getTagIds()))")
    Content toContent(ContentVideoCommand command);

}