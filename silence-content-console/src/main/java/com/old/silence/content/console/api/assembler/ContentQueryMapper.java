package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentQuery;

import com.old.silence.content.console.dto.ContentConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface ContentQueryMapper extends Converter<ContentConsoleQuery, ContentQuery> {


    @Override
    ContentQuery convert(ContentConsoleQuery source);
}
