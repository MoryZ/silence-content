package com.old.silence.content.console.api.assembler;

import java.lang.Override;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeFileTemplateCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.CodeFileTemplateConsoleCommand;


/**
 * FreemarkerTemplatesCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeFileTemplateCommandMapper extends Converter<CodeFileTemplateConsoleCommand, CodeFileTemplateCommand>{

    @Override
    CodeFileTemplateCommand convert(CodeFileTemplateConsoleCommand command);
}