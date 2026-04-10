package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.TournamentRobotInstanceCommand;
import com.old.silence.content.domain.model.tournament.TournamentRobotInstance;

@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface TournamentRobotInstanceMapper extends Converter<TournamentRobotInstanceCommand, TournamentRobotInstance> {

    @Override
    TournamentRobotInstance convert(TournamentRobotInstanceCommand source);
}
