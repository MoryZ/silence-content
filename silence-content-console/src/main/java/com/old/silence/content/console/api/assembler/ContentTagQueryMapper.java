package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.ContentTagQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.ContentTagConsoleQuery;

/**
 * @author MurrayZhang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentTagQueryMapper extends Converter<ContentTagConsoleQuery, ContentTagQuery> {



}
