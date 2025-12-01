package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentArticleCommand;
import com.old.silence.content.api.dto.ContentVideoCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.ContentArticleConsoleCommand;
import com.old.silence.content.console.dto.ContentVideoConsoleCommand;

/**
 * @author moryzang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentVideoCommandMapper extends Converter<ContentVideoConsoleCommand, ContentVideoCommand> {


    @Override
    ContentVideoCommand convert(ContentVideoConsoleCommand command);
}
