package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserFavoriteCommand;
import com.old.silence.content.application.api.dto.PoetryUserFavoriteApplicationCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserFavorite映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserFavoriteApplicationMapper extends Converter<PoetryUserFavoriteApplicationCommand, PoetryUserFavoriteCommand> {

    @Override
    PoetryUserFavoriteCommand convert(PoetryUserFavoriteApplicationCommand command);
}