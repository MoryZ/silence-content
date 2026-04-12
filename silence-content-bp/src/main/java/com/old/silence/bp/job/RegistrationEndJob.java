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
@JobExecutor(name = "registrationEndJob")
public class RegistrationEndJob {

    private static final Logger log = LoggerFactory.getLogger(RegistrationEndJob.class);

    private final TournamentTaskClient tournamentTaskClient;

    public RegistrationEndJob(TournamentTaskClient tournamentTaskClient) {
        this.tournamentTaskClient = tournamentTaskClient;
    }

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        log.info("registrationEndJob start, args={}", JacksonMapper.getSharedInstance().toJson(jobArgs));
        int handled = tournamentTaskClient.handleRegistrationEnd();
        return ExecuteResult.success("registrationEndJob done, handled=" + handled);
    }
}
