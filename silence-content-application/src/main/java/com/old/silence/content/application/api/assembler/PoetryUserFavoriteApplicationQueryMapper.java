package com.old.silence.content.application.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserFavoriteCommand;
import com.old.silence.content.api.dto.PoetryUserFavoriteQuery;
import com.old.silence.content.application.api.dto.PoetryUserFavoriteApplicationCommand;
import com.old.silence.content.application.api.dto.PoetryUserFavoriteApplicationQuery;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * PoetryUserFavorite映射器
 */
@Mapper(uses = MapStructSpringConfig.class)
public interface PoetryUserFavoriteApplicationQueryMapper extends Converter<PoetryUserFavoriteApplicationQuery, PoetryUserFavoriteQuery> {


}