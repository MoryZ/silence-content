package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import static com.old.silence.bp.job.TestAnnoJobExecutorSleep5s.JOB_NAME;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.client.core.executor.AbstractJobExecutor;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

import java.time.Duration;


/**
 * @author moryzang
 */
@Component
@JobExecutor(name = JOB_NAME)
public class TestAnnoJobExecutorSleep5s extends AbstractJobExecutor {

    public static final String JOB_NAME = "testAnnoJobExecutorSleep5s";
    private static final Logger log = LoggerFactory.getLogger(TestAnnoJobExecutorSleep5s.class);
    private final JacksonMapper jacksonMapper;

    public TestAnnoJobExecutorSleep5s(JacksonMapper jacksonMapper) {
        this.jacksonMapper = jacksonMapper;
    }


    @Override
    protected ExecuteResult doJobExecute(JobArgs jobArgs) {
        log.info("{}. param:{}", JOB_NAME, jacksonMapper.toJson(jobArgs));
        try {
            Thread.sleep(Duration.ofSeconds(5).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ExecuteResult.success(JOB_NAME + "执行成功");
    }

}