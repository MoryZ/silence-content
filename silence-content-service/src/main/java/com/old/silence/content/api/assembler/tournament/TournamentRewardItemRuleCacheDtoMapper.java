package com.old.silence.content.api.assembler.tournament;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRewardItemRuleCacheDto;
import com.old.silence.content.domain.model.MarketingRule;
import com.old.silence.core.mapstruct.MapStructSpringConfig;


@Mapper(uses = MapStructSpringConfig.class)
public interface TournamentRewardItemRuleCacheDtoMapper extends Converter<MarketingRule, TournamentRewardItemRuleCacheDto> {

}
