package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeGenModuleCommand;

import com.old.silence.content.console.dto.CodeGenModuleConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeGenModuleCommandMapper extends Converter<CodeGenModuleConsoleCommand, CodeGenModuleCommand> {


}
