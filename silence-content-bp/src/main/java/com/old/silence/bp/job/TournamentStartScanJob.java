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
 * 赛事开始扫描 Job
 * 每分钟扫描赛事开始前 10 分钟窗口，并创建 REGISTRATION_END 任务
 *
 * @author moryzang
 */
@Component
@JobExecutor(name = "tournamentStartScanJob")
public class TournamentStartScanJob {

    private static final Logger log = LoggerFactory.getLogger(TournamentStartScanJob.class);

    private final TournamentTaskClient tournamentTaskClient;

    public TournamentStartScanJob(TournamentTaskClient tournamentTaskClient) {
        this.tournamentTaskClient = tournamentTaskClient;
    }

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        log.info("tournamentStartScanJob start, args={}", JacksonMapper.getSharedInstance().toJson(jobArgs));
        int seeded = tournamentTaskClient.seedRegistrationEndTasks();
        log.info("tournamentStartScanJob done, seeded={}", seeded);
        return ExecuteResult.success("seeded=" + seeded);
    }
}
