package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.old.silence.content.api.assembler.support.ContentAccessMapper;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.ContentProductTermCommand;
import com.old.silence.content.api.dto.ContentVideoCommand;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.model.ContentProductTerm;
import com.old.silence.content.domain.model.ContentVideo;

/**
 * @author moryzang
 */

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentProductTermMapper extends ContentAccessMapper<ContentProductTermCommand, ContentProductTerm> {

    @Override
    @Mapping(target = "content", source = "command")
    ContentProductTerm convert(ContentProductTermCommand command);

    @Mapping(target = "contentContentTags", expression = "java(toContentContentTags(command.getTagIds()))")
    Content toContent(ContentVideoCommand command);

}