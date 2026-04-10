package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.TournamentParticipationRecordCommand;
import com.old.silence.content.domain.model.tournament.TournamentParticipationRecord;

/**
 * TournamentParticipationRecord映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface TournamentParticipationRecordMapper extends Converter<TournamentParticipationRecordCommand, TournamentParticipationRecord> {

    @Override
    TournamentParticipationRecord convert(TournamentParticipationRecordCommand command);
}
