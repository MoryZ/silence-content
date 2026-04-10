package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.old.silence.bp.mq.event.SegmentCompletedEvent;
import com.old.silence.bp.mq.producer.SegmentEventProducer;
import com.old.silence.content.api.TournamentConfigClient;
import com.old.silence.content.api.TournamentParticipationRecordClient;
import com.old.silence.content.api.dto.TournamentConfigQuery;
import com.old.silence.content.api.dto.TournamentParticipationRecordQuery;
import com.old.silence.content.api.vo.TournamentConfigView;
import com.old.silence.content.api.vo.TournamentParticipationRecordView;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

import java.time.Instant;
import java.util.List;

/**
 * @author moryzang
 */
@Component
@JobExecutor(name = "stageEndJob")
public class StageEndJob {

    private static final Logger log = LoggerFactory.getLogger(StageEndJob.class);

    private static final int PAGE_SIZE = 500;

    private final TournamentConfigClient tournamentConfigClient;

    private final TournamentParticipationRecordClient tournamentParticipationRecordClient;

    private final SegmentEventProducer segmentEventProducer;

    public StageEndJob(TournamentConfigClient tournamentConfigClient,
                       TournamentParticipationRecordClient tournamentParticipationRecordClient,
                       SegmentEventProducer segmentEventProducer) {
        this.tournamentConfigClient = tournamentConfigClient;
        this.tournamentParticipationRecordClient = tournamentParticipationRecordClient;
        this.segmentEventProducer = segmentEventProducer;
    }

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        log.info("stageEndJob start, args={}", JacksonMapper.getSharedInstance().toJson(jobArgs));
        int handled = 0;
        Instant now = Instant.now();

        TournamentConfigQuery query = new TournamentConfigQuery();
        List<TournamentConfigView> configs = tournamentConfigClient
                .query(query, PageRequest.of(0, PAGE_SIZE), TournamentConfigView.class)
                .getContent();

        for (TournamentConfigView config : configs) {
            if (!isStageWindowClosed(config, now)) {
                continue;
            }
            handled += processClosedStage(config);
        }
        return ExecuteResult.success("stageEndJob done, handled=" + handled);
    }

    private boolean isStageWindowClosed(TournamentConfigView config, Instant now) {
        return config.getTournamentEndTime() != null && now.isAfter(config.getTournamentEndTime());
    }

    private int processClosedStage(TournamentConfigView config) {
        TournamentParticipationRecordQuery query = new TournamentParticipationRecordQuery();
        query.setEventGameId(config.getEventGameId());
        query.setStatus(TournamentParticipantStatus.REGISTERED);
        List<TournamentParticipationRecordView> participants = tournamentParticipationRecordClient
                .query(query, PageRequest.of(0, PAGE_SIZE), TournamentParticipationRecordView.class)
                .getContent();

        log.info("stageEndJob stage closed, eventGameId={}, participants={}, action=score-calc-and-mq-dispatch-pending",
                config.getEventGameId(), participants.size());

        segmentEventProducer.send(new SegmentCompletedEvent(
            config.getEventGameId(),
            config.getTotalStages() == null ? 1 : config.getTotalStages(),
            Instant.now()));
        return 1;
    }

}
