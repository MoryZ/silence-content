package com.old.silence.content.console.api.assembler;

import java.lang.Override;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.FreemarkerTemplatesCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.FreemarkerTemplatesConsoleCommand;


/**
 * FreemarkerTemplatesCommand映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface FreemarkerTemplatesCommandMapper extends Converter<FreemarkerTemplatesConsoleCommand, FreemarkerTemplatesCommand>{

    @Override
    FreemarkerTemplatesCommand convert(FreemarkerTemplatesConsoleCommand command);
}