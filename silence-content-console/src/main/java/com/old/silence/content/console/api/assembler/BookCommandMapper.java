package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.BookCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.BookConsoleCommand;

/**
 * @author moryzang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface BookCommandMapper extends Converter<BookConsoleCommand, BookCommand> {


    @Override
    @Mapping(target = "status", constant = "STAGED")
    BookCommand convert(BookConsoleCommand source);
}
