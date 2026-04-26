package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.old.silence.bp.service.TournamentTaskDispatchService;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.common.client.dto.ExecuteResult;

/**
 * @author EX-ZHANGMENGWEI001
 */
@JobExecutor(name = "tournamentTaskDispatchJob")
@Component
public class TournamentTaskDispatchJob {

    private static final String PREFIX = "TournamentTaskDispatchJob";
    private static final Logger log = LoggerFactory.getLogger(TournamentTaskDispatchJob.class);

    private final TournamentTaskDispatchService tournamentTaskDispatchService;

    public TournamentTaskDispatchJob(TournamentTaskDispatchService tournamentTaskDispatchService) {
        this.tournamentTaskDispatchService = tournamentTaskDispatchService;
    }


    public ExecuteResult jobExecute(JobArgs jobParamVO) throws Exception {
        log.info(PREFIX +" start");
        tournamentTaskDispatchService.dispatchPendingTasks();
        log.info(PREFIX + "end");
        return ExecuteResult.success(PREFIX + "end");
    }
}
