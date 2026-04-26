package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

/**
 * @author moryzang
 */
@Component
// @JobExecutor(name = "paramJob")
public class ParamHandler {

    private static final Logger log = LoggerFactory.getLogger(ParamHandler.class);

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        log.info("param:{}", JacksonMapper.getSharedInstance().toJson(jobArgs));
        return ExecuteResult.success("测试获取成功");
    }


}