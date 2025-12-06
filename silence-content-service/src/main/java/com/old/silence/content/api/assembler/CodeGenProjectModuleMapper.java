package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.CodeGenProjectModuleCommand;
import com.old.silence.content.domain.model.codegen.CodeGenProjectModule;

/**
 * @author moryzang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeGenProjectModuleMapper extends Converter<CodeGenProjectModuleCommand, CodeGenProjectModule> {


}
