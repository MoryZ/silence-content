package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.BookCommand;
import com.old.silence.content.domain.model.Book;

/**
 * Book映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface BookMapper extends Converter<BookCommand, Book> {

    @Override
    Book convert(BookCommand command);
}