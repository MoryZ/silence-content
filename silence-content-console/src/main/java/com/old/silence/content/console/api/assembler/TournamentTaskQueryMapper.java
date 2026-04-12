package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.TournamentTaskQuery;
import com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.console.dto.TournamentTaskConsoleQuery;

/**
 * TournamentTask查询映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface TournamentTaskQueryMapper extends Converter<TournamentTaskConsoleQuery, TournamentTaskQuery> {
}