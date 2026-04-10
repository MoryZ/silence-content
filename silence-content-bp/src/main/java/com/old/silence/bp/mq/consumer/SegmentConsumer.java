package com.old.silence.bp.mq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.old.silence.bp.mq.event.CycleCompletedEvent;
import com.old.silence.bp.mq.event.SegmentCompletedEvent;
import com.old.silence.bp.mq.producer.CycleEventProducer;
import com.old.silence.content.api.TournamentParticipationRecordClient;
import com.old.silence.content.api.dto.TournamentParticipationRecordQuery;
import com.old.silence.content.api.vo.TournamentParticipationRecordView;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @author moryzang
 */
@Component
public class SegmentConsumer {

    private static final Logger log = LoggerFactory.getLogger(SegmentConsumer.class);

    private static final int PAGE_SIZE = 1000;

    private final TournamentParticipationRecordClient tournamentParticipationRecordClient;

    private final CycleEventProducer cycleEventProducer;

    public SegmentConsumer(TournamentParticipationRecordClient tournamentParticipationRecordClient,
                           CycleEventProducer cycleEventProducer) {
        this.tournamentParticipationRecordClient = tournamentParticipationRecordClient;
        this.cycleEventProducer = cycleEventProducer;
    }

    @EventListener
    public void onSegmentCompleted(SegmentCompletedEvent event) {
        TournamentParticipationRecordQuery query = new TournamentParticipationRecordQuery();
        query.setEventGameId(event.getEventGameId());
        query.setStatus(TournamentParticipantStatus.REGISTERED);

        List<TournamentParticipationRecordView> participants = tournamentParticipationRecordClient
                .query(query, PageRequest.of(0, PAGE_SIZE), TournamentParticipationRecordView.class)
                .getContent();

        participants.sort(Comparator.comparing(
                item -> item.getTotalScore() == null ? BigDecimal.ZERO : item.getTotalScore(),
                Comparator.reverseOrder()));

        log.info("segment consumed, eventGameId={}, stageNumber={}, participantCount={}, topParticipantId={}",
                event.getEventGameId(),
                event.getStageNumber(),
                participants.size(),
                participants.isEmpty() ? "N/A" : participants.getFirst().getParticipantId());

        cycleEventProducer.send(new CycleCompletedEvent(event.getEventGameId(), 1, event.getOccurredAt()));
    }

}
