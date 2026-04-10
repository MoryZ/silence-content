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
 * 赛事任务调度 Job
 * 每分钟扫描一次待执行任务并进行调度
 *
 * @author moryzang
 */
@Component
@JobExecutor(name = "tournamentTaskDispatchJob")
public class TournamentTaskDispatchJob {

    private static final Logger log = LoggerFactory.getLogger(TournamentTaskDispatchJob.class);

    private final TournamentTaskClient tournamentTaskClient;

    public TournamentTaskDispatchJob(TournamentTaskClient tournamentTaskClient) {
        this.tournamentTaskClient = tournamentTaskClient;
    }

    /**
     * Job 执行入口
     *
     * @param jobArgs 任务参数
     * @return 执行结果
     */
    public ExecuteResult jobExecute(JobArgs jobArgs) {
        log.info("tournamentTaskDispatchJob start, args={}", JacksonMapper.getSharedInstance().toJson(jobArgs));
        int dispatched = tournamentTaskClient.dispatchPendingTasks();
        log.info("tournamentTaskDispatchJob done, dispatched={}", dispatched);
        return ExecuteResult.success("dispatched=" + dispatched);
    }
}
