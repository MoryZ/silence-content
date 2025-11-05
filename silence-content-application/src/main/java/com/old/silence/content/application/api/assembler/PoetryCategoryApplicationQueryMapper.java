package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryCategoryQuery;
import com.old.silence.content.application.api.dto.PoetryCategoryApplicationQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryCategory映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryCategoryApplicationQueryMapper extends Converter<PoetryCategoryApplicationQuery, PoetryCategoryQuery> {

    @Override
    PoetryCategoryQuery convert(PoetryCategoryApplicationQuery query);
}