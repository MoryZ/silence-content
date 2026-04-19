package com.old.silence.content.console.service;

import org.springframework.stereotype.Service;
import com.old.silence.content.api.EventGameRewardItemDomainClient;
import com.old.silence.content.api.MarketingEventDomainClient;
import com.old.silence.content.api.TournamentConfigDomainClient;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigDomainCommand;
import com.old.silence.content.console.vo.EventGameConsoleView;
import com.old.silence.content.console.vo.EventGameRewardItemConsoleView;
import com.old.silence.content.console.vo.MarketingEventAndEventGamesConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;

import com.old.silence.content.console.vo.TournamentConfigConsoleView;
import com.old.silence.content.console.vo.TournamentConfigDetailConsoleVO;


import java.math.BigInteger;

/**
 * @author EX-ZHANGMENGWEI001
 */
@Service
public class TournamentConfigConsoleService {

    private final TournamentConfigDomainClient tournamentConfigDomainClient;
    private final MarketingEventDomainClient marketingEventDomainClient;
    private final EventGameRewardItemDomainClient eventGameRewardItemDomainClient;
    public TournamentConfigConsoleService(TournamentConfigDomainClient tournamentConfigDomainClient,
                                          MarketingEventDomainClient marketingEventDomainClient,
                                          EventGameRewardItemDomainClient eventGameRewardItemDomainClient) {
        this.tournamentConfigDomainClient = tournamentConfigDomainClient;
        this.marketingEventDomainClient = marketingEventDomainClient;
        this.eventGameRewardItemDomainClient = eventGameRewardItemDomainClient;
    }

    public TournamentConfigDetailConsoleVO findByEventId(BigInteger eventId) {
        var marketingEvent = marketingEventDomainClient.findById(eventId, MarketingEventAndEventGamesConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
        var eventGameId = CollectionUtils.firstElement(marketingEvent.getEventGames()).map(EventGameConsoleView::getId)
                .orElseThrow(ResourceNotFoundException::new);

        var tournamentConfig = tournamentConfigDomainClient.findByEventGameId(eventGameId, TournamentConfigConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
        var eventGameRewardItems = eventGameRewardItemDomainClient.findByEventGameId(eventGameId, EventGameRewardItemConsoleView.class);

        return new TournamentConfigDetailConsoleVO(tournamentConfig, marketingEvent, eventGameRewardItems);
    }

    public BigInteger create(TournamentConfigDomainCommand command) {
        return tournamentConfigDomainClient.create(command);
    }

    public int update(TournamentConfigDomainCommand tournamentConfigDomainCommand) {
        var eventGameId = tournamentConfigDomainCommand.getEventGameId();
        var tournamentConfig = tournamentConfigDomainClient.findByEventGameId(eventGameId, TournamentConfigConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
        var marketingEvent = marketingEventDomainClient.findById(tournamentConfigDomainCommand.getMarketingEvent().getId(),
                        MarketingEventAndEventGamesConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
      /*   if (Instant.now().plus(Duration.ofMinutes(10)).isAfter(marketingEvent.getStartTime())) {
            throw new EhisException(MarketingMessages.FORBID_MODIFY_BEFORE_TOURNAMENT_START);
        } */

        return tournamentConfigDomainClient.update(tournamentConfig.getId(), tournamentConfigDomainCommand);
    }


}
