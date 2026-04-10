package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.TournamentChallengeRecordCommand;
import com.old.silence.content.domain.model.tournament.TournamentChallengeRecord;

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface TournamentChallengeRecordMapper extends Converter<TournamentChallengeRecordCommand, TournamentChallengeRecord> {

    @Override
    TournamentChallengeRecord convert(TournamentChallengeRecordCommand source);
}
