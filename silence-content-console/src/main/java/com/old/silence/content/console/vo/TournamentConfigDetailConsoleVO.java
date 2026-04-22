package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.content.api.tournament.tournament.dto.support.CycleStageConfig;
import com.old.silence.content.console.dto.EventGameRewardItemAndRuleConsoleCommand;
import com.old.silence.content.console.dto.EventGameRewardItemRuleConsoleCommand;
import com.old.silence.content.domain.enums.tournament.TournamentDivisionMode;
import com.old.silence.content.domain.enums.tournament.TournamentMatchMode;
import com.old.silence.content.domain.enums.tournament.TournamentRobotMode;
import com.old.silence.content.domain.enums.tournament.TournamentType;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;


import java.math.BigInteger;
import java.time.Instant;
import java.util.List;


public class TournamentConfigDetailConsoleVO {


    private final TournamentConfigConsoleView tournamentConfigDb;

    private final MarketingEventAndEventGamesConsoleView marketingEvent;

    private final List<EventGameRewardItemConsoleView> eventGameRewardItemsDb;

    public TournamentConfigDetailConsoleVO(TournamentConfigConsoleView tournamentConfigDb,
                                           MarketingEventAndEventGamesConsoleView marketingEvent,
                                           List<EventGameRewardItemConsoleView> eventGameRewardItemsDb) {
        this.tournamentConfigDb = tournamentConfigDb;
        this.marketingEvent = marketingEvent;
        this.eventGameRewardItemsDb = eventGameRewardItemsDb;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public BigInteger getEventGameId() {
        return tournamentConfigDb.getEventGameId();
    }


    public TournamentType getTournamentType() {
        return tournamentConfigDb.getTournamentType();
    }


    public TournamentDivisionMode getDivisionMode() {
        return tournamentConfigDb.getDivisionMode();
    }

    public TournamentMatchMode getMatchMode() {
        return tournamentConfigDb.getMatchMode();
    }

    public TournamentRobotMode getRobotMode() {
        return tournamentConfigDb.getRobotMode();
    }


    public Instant getRegistrationStartTime() {
        return tournamentConfigDb.getRegistrationStartTime();
    }

    public Instant getRegistrationEndTime() {
        return tournamentConfigDb.getRegistrationEndTime();
    }

    public Instant getTournamentStartTime() {
        return tournamentConfigDb.getTournamentStartTime();
    }

    public Instant getTournamentEndTime() {
        return tournamentConfigDb.getTournamentEndTime();
    }

    public Integer getMinParticipants() {
        return tournamentConfigDb.getMinParticipants();
    }

    public Integer getMaxParticipants() {
        return tournamentConfigDb.getMaxParticipants();
    }

    public Integer getGroupSize() {
        return tournamentConfigDb.getGroupSize();
    }

    public Integer getMaxDailyChallenges() {
        return tournamentConfigDb.getMaxDailyChallenges();
    }

    public Integer getChallengeTimeoutMinutes() {
        return tournamentConfigDb.getChallengeTimeoutMinutes();
    }

    public MarketingEventAndEventGamesConsoleView getMarketingEvent() {
        return marketingEvent;
    }

    public List<EventGameRewardItemAndRuleConsoleCommand> getEventGameRewardItems() {
        return CollectionUtils.transformToList(eventGameRewardItemsDb, eventGameRewardItemConsoleView -> {
                    var eventGameRewardItemAndRuleConsoleCommand = new EventGameRewardItemAndRuleConsoleCommand();
                    eventGameRewardItemAndRuleConsoleCommand.setId(eventGameRewardItemConsoleView.getId());
                    eventGameRewardItemAndRuleConsoleCommand.setClaimType(eventGameRewardItemConsoleView.getClaimType());
                    eventGameRewardItemAndRuleConsoleCommand.setRewardItemId(eventGameRewardItemConsoleView.getRewardItemId());
                    eventGameRewardItemAndRuleConsoleCommand.setQuantity(eventGameRewardItemConsoleView.getQuantity());
                    eventGameRewardItemAndRuleConsoleCommand.setInventoryQuantity(eventGameRewardItemConsoleView.getInventoryQuantity());
                    eventGameRewardItemAndRuleConsoleCommand.setEventGameRewardItemRule(JacksonMapper.getSharedInstance().unwrap().convertValue(eventGameRewardItemConsoleView.getAttributes(),
                    EventGameRewardItemRuleConsoleCommand.class));
                    return eventGameRewardItemAndRuleConsoleCommand;
                }
        );
    }


    public CycleStageConfig getCycleStageConfig() {
        return JacksonMapper.getSharedInstance().unwrap().convertValue(tournamentConfigDb.getAttributes(), CycleStageConfig.class);
    }


}
