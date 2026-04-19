package com.old.silence.content.console.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.old.silence.content.api.dto.EventGameRewardItemDomainCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigDomainCommand;
import com.old.silence.content.console.dto.TournamentConfigConsoleCommand;
import com.old.silence.core.mapstruct.MapStructSpringConfig;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;


import java.util.List;
import java.util.Map;

@Mapper(uses = MapStructSpringConfig.class)
public interface TournamentConfigDomainCommandMapper extends Converter<TournamentConfigConsoleCommand, TournamentConfigDomainCommand> {

    @Override
    @Mapping(target = "attributes", expression = "java(toMapAttributes(command))")
    @Mapping(target = "eventGameRewardItems", expression = "java(toEventGameRewardItems(command))")
    @Mapping(target = "marketingEvent.backgroundImageIobsKey", source = "command.marketingEvent.backgroundImageUrl")
    TournamentConfigDomainCommand convert(TournamentConfigConsoleCommand command);

    default Map<String, Object> toMapAttributes(TournamentConfigConsoleCommand command) {
        return JacksonMapper.getSharedInstance().unwrap().convertValue(command.getCycleStageConfig(), new TypeReference<>() {});
    }

    default List<EventGameRewardItemDomainCommand> toEventGameRewardItems(TournamentConfigConsoleCommand command) {

        return CollectionUtils.transformToList(command.getEventGameRewardItems(), eventGameRewardItem -> {
            var eventGameRewardItemDomainCommand = new EventGameRewardItemDomainCommand();
            eventGameRewardItemDomainCommand.setEventGameId(command.getEventGameId());
            eventGameRewardItemDomainCommand.setRewardItemId(eventGameRewardItem.getRewardItemId());
            eventGameRewardItemDomainCommand.setQuantity(eventGameRewardItem.getQuantity());
            eventGameRewardItemDomainCommand.setInventoryQuantity(eventGameRewardItem.getInventoryQuantity());
            eventGameRewardItemDomainCommand.setClaimType(eventGameRewardItem.getClaimType());
            eventGameRewardItemDomainCommand.setAttributes(JacksonMapper.getSharedInstance().unwrap().convertValue(eventGameRewardItem.getEventGameRewardItemRule(), new TypeReference<>() {
            }));
            return eventGameRewardItemDomainCommand;
        });
    }
}


