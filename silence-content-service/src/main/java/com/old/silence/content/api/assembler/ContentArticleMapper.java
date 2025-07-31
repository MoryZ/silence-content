package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.assembler.support.ContentAccessMapper;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.ContentArticleCommand;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.model.ContentArticle;

/**
 * @author moryzang
 */

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentArticleMapper extends ContentAccessMapper<ContentArticleCommand, ContentArticle> {

    @Override
    @Mapping(target = "content", source = "command")
    ContentArticle convert(ContentArticleCommand command);

    @Mapping(target = "contentContentTags", expression = "java(toContentContentTags(command.getTagIds()))")
    Content toContent(ContentArticleCommand command);

}