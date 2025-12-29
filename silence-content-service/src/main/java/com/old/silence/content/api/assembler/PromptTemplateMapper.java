package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PromptTemplateCommand;
import com.old.silence.content.domain.model.poetry.PromptTemplate;

/**
 * PromptTemplate映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PromptTemplateMapper extends Converter<PromptTemplateCommand, PromptTemplate> {

    @Override
    PromptTemplate convert(PromptTemplateCommand command);
}