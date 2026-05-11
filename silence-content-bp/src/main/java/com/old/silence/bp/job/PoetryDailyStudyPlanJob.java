package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.bp.service.PoetryDailyStudyPlanService;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.client.core.executor.AbstractJobExecutor;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

/**
 * @author moryzang
 */
@Component
@JobExecutor(name = PoetryDailyStudyPlanJob.POETRY_DAILY_STUDY_PLAN_JOB)
public class PoetryDailyStudyPlanJob extends AbstractJobExecutor {

    private static final Logger log = LoggerFactory.getLogger(PoetryDailyStudyPlanJob.class);
    public static final String POETRY_DAILY_STUDY_PLAN_JOB = "poetryDailyStudyPlanJob";

    private final PoetryDailyStudyPlanService poetryDailyStudyPlanService;
    private final JacksonMapper jacksonMapper;

    public PoetryDailyStudyPlanJob(PoetryDailyStudyPlanService poetryDailyStudyPlanService,
                                   JacksonMapper jacksonMapper) {
        this.poetryDailyStudyPlanService = poetryDailyStudyPlanService;
        this.jacksonMapper = jacksonMapper;
    }

    @Override
    protected ExecuteResult doJobExecute(JobArgs jobArgs) {
        log.info("{} param:{}", POETRY_DAILY_STUDY_PLAN_JOB, jacksonMapper.toJson(jobArgs));
        return poetryDailyStudyPlanService.adjustPlan();
    }


}