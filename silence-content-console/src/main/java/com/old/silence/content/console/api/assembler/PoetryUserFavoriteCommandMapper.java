package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.PoetryUserFavoriteCommand;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.PoetryUserFavoriteConsoleCommand;

/**
 * PoetryUserFavorite映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface PoetryUserFavoriteCommandMapper extends Converter<PoetryUserFavoriteConsoleCommand, PoetryUserFavoriteCommand> {


}