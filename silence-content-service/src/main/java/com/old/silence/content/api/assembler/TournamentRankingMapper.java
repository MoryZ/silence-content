package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.TournamentRankingCommand;
import com.old.silence.content.domain.model.tournament.TournamentRanking;

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface TournamentRankingMapper extends Converter<TournamentRankingCommand, TournamentRanking> {

    @Override
    TournamentRanking convert(TournamentRankingCommand source);
}
