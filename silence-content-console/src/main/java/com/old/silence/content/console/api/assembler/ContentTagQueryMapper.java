package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentTagQuery;

import com.old.silence.content.console.dto.ContentTagConsoleQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface ContentTagQueryMapper extends Converter<ContentTagConsoleQuery, ContentTagQuery> {


}
