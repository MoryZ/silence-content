package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.client.core.executor.AbstractJobExecutor;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

/**
 * @author moryzang
 */
@Component
@JobExecutor(name = ParamJob.JOB_NAME)
public class ParamJob extends AbstractJobExecutor {

    public static final String JOB_NAME = "paramJob";
    private static final Logger log = LoggerFactory.getLogger(ParamJob.class);
    private final JacksonMapper jacksonMapper;

    public ParamJob(JacksonMapper jacksonMapper) {
        this.jacksonMapper = jacksonMapper;
    }


    @Override
    protected ExecuteResult doJobExecute(JobArgs jobArgs) {
        log.info("{}. param:{}", JOB_NAME, jacksonMapper.toJson(jobArgs));
        return ExecuteResult.success("测试获取成功");
    }

}