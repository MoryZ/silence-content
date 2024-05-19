package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.domain.model.Content;

/**
 * @author MurrayZhang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentMapper extends Converter<ContentCommand, Content> {



}
