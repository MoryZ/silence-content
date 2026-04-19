package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.dto.EventGameRewardItemDomainCommand;
import com.old.silence.content.domain.model.EventGameRewardItem;
import com.old.silence.core.mapstruct.MapStructSpringConfig;


@Mapper(uses = MapStructSpringConfig.class)
public interface EventGameRewardItemMapper extends Converter<EventGameRewardItemDomainCommand, EventGameRewardItem> {


}
