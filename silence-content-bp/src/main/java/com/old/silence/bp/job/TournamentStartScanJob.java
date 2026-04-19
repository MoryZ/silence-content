package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.bp.service.TournamentStartScanService;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;

/**
 * @author EX-ZHANGMENGWEI001
 */
@JobExecutor(name = "tournamentStartScanJob")
@Component
public class TournamentStartScanJob {

    private static final String PREFIX = "TournamentStartScanJob";
    private static final Logger log = LoggerFactory.getLogger(TournamentStartScanJob.class);

    private final TournamentStartScanService tournamentStartScanService;

    public TournamentStartScanJob(TournamentStartScanService tournamentStartScanService) {
        this.tournamentStartScanService = tournamentStartScanService;
    }


    public String execute(JobArgs jobParamVO) throws Exception {
        log.info(PREFIX +" start");
        tournamentStartScanService.scanTournamentJobStart();
        log.info(PREFIX + "end");
        return PREFIX;
    }
}
