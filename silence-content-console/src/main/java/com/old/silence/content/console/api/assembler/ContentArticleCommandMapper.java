package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentArticleCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.ContentArticleConsoleCommand;

/**
 * @author moryzang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentArticleCommandMapper extends Converter<ContentArticleConsoleCommand, ContentArticleCommand> {


    @Override
    ContentArticleCommand convert(ContentArticleConsoleCommand command);
}
