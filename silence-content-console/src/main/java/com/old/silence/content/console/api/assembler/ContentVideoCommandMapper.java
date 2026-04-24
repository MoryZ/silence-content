package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentVideoCommand;

import com.old.silence.content.console.dto.ContentVideoConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface ContentVideoCommandMapper extends Converter<ContentVideoConsoleCommand, ContentVideoCommand> {


    @Override
    ContentVideoCommand convert(ContentVideoConsoleCommand command);
}
