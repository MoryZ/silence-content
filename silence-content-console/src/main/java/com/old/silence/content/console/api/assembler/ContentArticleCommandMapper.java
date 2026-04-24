package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentArticleCommand;

import com.old.silence.content.console.dto.ContentArticleConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface ContentArticleCommandMapper extends Converter<ContentArticleConsoleCommand, ContentArticleCommand> {


    @Override
    ContentArticleCommand convert(ContentArticleConsoleCommand command);
}
