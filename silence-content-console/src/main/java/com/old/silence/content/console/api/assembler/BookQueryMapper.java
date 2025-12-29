package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.BookQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.BookConsoleQuery;

/**
 * BookCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface BookQueryMapper extends Converter<BookConsoleQuery, BookQuery> {

    @Override
    BookQuery convert(BookConsoleQuery query);
}