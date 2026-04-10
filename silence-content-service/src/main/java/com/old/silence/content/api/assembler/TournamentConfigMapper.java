package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.TournamentConfigCommand;
import com.old.silence.content.domain.model.tournament.TournamentConfig;

/**
 * TournamentConfig映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface TournamentConfigMapper extends Converter<TournamentConfigCommand, TournamentConfig> {

    @Override
    TournamentConfig convert(TournamentConfigCommand command);
}
