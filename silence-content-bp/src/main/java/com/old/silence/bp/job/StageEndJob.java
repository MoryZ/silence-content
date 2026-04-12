package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.content.api.TournamentTaskClient;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

/**
 * @author moryzang
 */
@Component
@JobExecutor(name = "stageEndJob")
public class StageEndJob {

    private static final Logger log = LoggerFactory.getLogger(StageEndJob.class);

    private final TournamentTaskClient tournamentTaskClient;

    public StageEndJob(TournamentTaskClient tournamentTaskClient) {
        this.tournamentTaskClient = tournamentTaskClient;
    }

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        log.info("stageEndJob start, args={}", JacksonMapper.getSharedInstance().toJson(jobArgs));
        int handled = tournamentTaskClient.handleStageEnd();
        return ExecuteResult.success("stageEndJob done, handled=" + handled);
    }
}
