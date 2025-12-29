package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PromptCommonFormatCommand;
import com.old.silence.content.domain.model.poetry.PromptCommonFormat;

/**
 * PromptCommonFormat映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PromptCommonFormatMapper extends Converter<PromptCommonFormatCommand, PromptCommonFormat> {

    @Override
    PromptCommonFormat convert(PromptCommonFormatCommand command);
}