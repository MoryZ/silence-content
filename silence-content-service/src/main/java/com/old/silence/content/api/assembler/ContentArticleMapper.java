package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.old.silence.content.api.assembler.support.ContentAccessMapper;
import com.old.silence.content.api.dto.ContentArticleCommand;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.model.ContentArticle;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */

@Mapper(uses = MapStructSpringConfig.class)
public interface ContentArticleMapper extends ContentAccessMapper<ContentArticleCommand, ContentArticle> {

    @Override
    @Mapping(target = "content", source = "command")
    ContentArticle convert(ContentArticleCommand command);

    @Mapping(target = "contentContentTags", expression = "java(toContentContentTags(command.getTagIds()))")
    Content toContent(ContentArticleCommand command);

}