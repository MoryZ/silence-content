package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.old.silence.content.api.assembler.support.ContentAccessMapper;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.ContentArticleCommand;
import com.old.silence.content.api.dto.ContentLiveCommand;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.model.ContentArticle;
import com.old.silence.content.domain.model.ContentLive;

/**
 * @author MurrayZhang
 */

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentLiveMapper extends ContentAccessMapper<ContentLiveCommand, ContentLive> {

    @Override
    @Mapping(target = "content", source = "command")
    @Mapping(target = "tabularImageReferenceMode", constant = "EXTERNAL_LINK")
    ContentLive convert(ContentLiveCommand command);

    @Mapping(target = "contentContentTags", expression = "java(toContentContentTags(command.getTagIds()))")
    Content toContent(ContentLiveCommand command);

}