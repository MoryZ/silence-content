package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeGenModuleCommand;
import com.old.silence.content.domain.model.codegen.CodeGenModule;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeGenModuleMapper extends Converter<CodeGenModuleCommand, CodeGenModule> {


}
