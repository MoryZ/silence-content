package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.ContentConsoleCommand;

/**
 * @author MurrayZhang
 * @Description
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentCommandMapper extends Converter<ContentConsoleCommand, ContentCommand> {


    @Override
    @Mapping(target = "status", defaultValue = "STAGED")
    ContentCommand convert(ContentConsoleCommand source) ;
}
