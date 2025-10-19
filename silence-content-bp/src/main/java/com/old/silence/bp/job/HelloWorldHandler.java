package com.old.silence.bp.job;

import org.springframework.stereotype.Component;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.common.client.dto.ExecuteResult;

/**
 * @author moryzang
 */
@Component
@JobExecutor(name = "testJob")
public class HelloWorldHandler {


    public ExecuteResult jobExecute(JobArgs jobArgs) {
        System.out.println(jobArgs.getJobId());
        return ExecuteResult.success("测试成功");
    }


}