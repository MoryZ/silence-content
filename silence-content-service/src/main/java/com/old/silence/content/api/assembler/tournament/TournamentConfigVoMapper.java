package com.old.silence.content.api.assembler.tournament;


import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigCacheDto;
import com.old.silence.content.api.tournament.tournament.vo.TournamentConfigVo;
import com.old.silence.core.mapstruct.MapStructSpringConfig;


@Mapper(uses = MapStructSpringConfig.class)
public interface TournamentConfigVoMapper extends Converter<TournamentConfigCacheDto, TournamentConfigVo> {

}
