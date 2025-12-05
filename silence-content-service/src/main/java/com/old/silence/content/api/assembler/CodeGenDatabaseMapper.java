package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.CodeGenDatabaseCommand;
import com.old.silence.content.domain.model.codegen.CodeGenDatabase;

/**
 * @author moryzang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeGenDatabaseMapper extends Converter<CodeGenDatabaseCommand, CodeGenDatabase> {


}
