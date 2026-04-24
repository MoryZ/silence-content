package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.CodeTableMetaCommand;
import com.old.silence.content.domain.model.CodeTableMeta;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * CodeTableMeta映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface CodeTableMetaMapper extends Converter<CodeTableMetaCommand, CodeTableMeta> {

    @Override
    CodeTableMeta convert(CodeTableMetaCommand command);
}