package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeGenProjectModuleCommand;

import com.old.silence.content.console.dto.CodeGenProjectModuleConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeGenProjectModuleCommandMapper extends Converter<CodeGenProjectModuleConsoleCommand, CodeGenProjectModuleCommand> {


}
