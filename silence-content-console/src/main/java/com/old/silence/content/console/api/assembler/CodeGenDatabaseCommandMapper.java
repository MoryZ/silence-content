package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeGenDatabaseCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.CodeGenDatabaseConsoleCommand;

/**
 * @author moryzang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeGenDatabaseCommandMapper extends Converter<CodeGenDatabaseConsoleCommand, CodeGenDatabaseCommand> {


}
