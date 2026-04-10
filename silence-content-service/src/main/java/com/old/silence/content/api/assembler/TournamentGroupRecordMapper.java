package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.TournamentGroupRecordCommand;
import com.old.silence.content.domain.model.tournament.TournamentGroupRecord;

/**
 * TournamentGroupRecord映射器
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface TournamentGroupRecordMapper extends Converter<TournamentGroupRecordCommand, TournamentGroupRecord> {

    @Override
    TournamentGroupRecord convert(TournamentGroupRecordCommand command);
}
