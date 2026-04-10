package com.old.silence.bp.mq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import com.old.silence.bp.mq.event.CycleCompletedEvent;
import com.old.silence.content.api.TournamentGroupClient;
import com.old.silence.content.api.dto.TournamentGroupQuery;
import com.old.silence.content.api.vo.TournamentGroupView;

import java.util.List;

/**
 * @author moryzang
 */
@Component
public class CycleConsumer {

    private static final Logger log = LoggerFactory.getLogger(CycleConsumer.class);

    private static final int PAGE_SIZE = 500;

    private final TournamentGroupClient tournamentGroupClient;

    public CycleConsumer(TournamentGroupClient tournamentGroupClient) {
        this.tournamentGroupClient = tournamentGroupClient;
    }

    @EventListener
    public void onCycleCompleted(CycleCompletedEvent event) {
        TournamentGroupQuery query = new TournamentGroupQuery();
        query.setEventGameId(event.getEventGameId());

        List<TournamentGroupView> groups = tournamentGroupClient
                .query(query, PageRequest.of(0, PAGE_SIZE), TournamentGroupView.class)
                .getContent();

        log.info("cycle consumed, eventGameId={}, cycleNumber={}, groupCount={}, action=regroup-pending",
                event.getEventGameId(), event.getCycleNumber(), groups.size());
    }

}
