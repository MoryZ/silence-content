package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeFileTemplateQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.CodeFileTemplateConsoleQuery;


/**
 * FreemarkerTemplatesCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeFileTemplateQueryMapper extends Converter<CodeFileTemplateConsoleQuery, CodeFileTemplateQuery> {

    @Override
    CodeFileTemplateQuery convert(CodeFileTemplateConsoleQuery query);
}