package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.ContentTagCommand;
import com.old.silence.content.domain.model.ContentTag;

/**
 * @author MurrayZhang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface ContentTagMapper extends Converter<ContentTagCommand, ContentTag> {


}
