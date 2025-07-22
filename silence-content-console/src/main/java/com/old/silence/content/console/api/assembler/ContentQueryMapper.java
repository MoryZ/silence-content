package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.ContentConsoleQuery;

/**
 * @author MurrayZhang
 * @Description
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentQueryMapper extends Converter<ContentConsoleQuery, ContentQuery> {


    @Override
    ContentQuery convert(ContentConsoleQuery source);
}
