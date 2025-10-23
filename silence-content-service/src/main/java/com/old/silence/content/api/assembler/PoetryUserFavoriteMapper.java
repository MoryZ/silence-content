package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.PoetryUserFavoriteCommand;
import com.old.silence.content.domain.model.PoetryUserFavorite;

/**
* PoetryUserFavorite映射器
*/
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserFavoriteMapper extends Converter<PoetryUserFavoriteCommand, PoetryUserFavorite>{

        @Override
        PoetryUserFavorite convert(PoetryUserFavoriteCommand command);
}