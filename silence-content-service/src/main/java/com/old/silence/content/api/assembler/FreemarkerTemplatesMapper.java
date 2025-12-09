package com.old.silence.content.api.assembler;

import java.lang.Override;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.FreemarkerTemplatesCommand;
import com.old.silence.content.domain.model.codegen.FreemarkerTemplates;


/**
 * FreemarkerTemplates映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface FreemarkerTemplatesMapper extends Converter<FreemarkerTemplatesCommand, FreemarkerTemplates>{

    @Override
    FreemarkerTemplates convert(FreemarkerTemplatesCommand command);
}
