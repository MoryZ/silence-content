package com.old.silence.content.api.assembler.tournament;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupRecordCommand;
import com.old.silence.content.domain.model.TournamentGroupRecord;
import com.old.silence.core.mapstruct.MapStructSpringConfig;


@Mapper(uses = MapStructSpringConfig.class)
public interface TournamentGroupRecordMapper extends Converter<TournamentGroupRecordCommand, TournamentGroupRecord> {


}
