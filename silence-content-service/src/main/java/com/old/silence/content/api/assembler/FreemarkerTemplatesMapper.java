package com.old.silence.content.api.assembler;

import java.lang.Override;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.CodeFileTemplateCommand;
import com.old.silence.content.domain.model.codegen.CodeFileTemplate;


/**
 * FreemarkerTemplates映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface FreemarkerTemplatesMapper extends Converter<CodeFileTemplateCommand, CodeFileTemplate>{

    @Override
    CodeFileTemplate convert(CodeFileTemplateCommand command);
}
