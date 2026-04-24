package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeGenProjectModuleCommand;
import com.old.silence.content.domain.model.codegen.CodeGenProjectModule;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeGenProjectModuleMapper extends Converter<CodeGenProjectModuleCommand, CodeGenProjectModule> {


}
