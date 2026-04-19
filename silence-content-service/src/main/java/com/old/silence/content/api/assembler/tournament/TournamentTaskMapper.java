package com.old.silence.content.api.assembler.tournament;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;


import com.old.silence.content.api.tournament.tournament.dto.TournamentTaskDomainCommand;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

import javax.annotation.Nonnull;

@Mapper(uses = MapStructSpringConfig.class)
public interface TournamentTaskMapper extends Converter<TournamentTaskDomainCommand, TournamentTask> {


    @Nonnull
    @Override
    @Mapping(target = "retryCount", constant = "0")
    @Mapping(target = "dependsOnStatus", constant = "SUCCESS")
    TournamentTask convert(TournamentTaskDomainCommand source);
}
