package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeGenProjectCommand;
import com.old.silence.content.domain.model.codegen.CodeGenProject;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeGenProjectMapper extends Converter<CodeGenProjectCommand, CodeGenProject> {


}
