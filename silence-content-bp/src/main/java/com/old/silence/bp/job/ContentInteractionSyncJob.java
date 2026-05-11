package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.bp.service.ContentInteractionSyncService;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.client.core.executor.AbstractJobExecutor;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

/**
 * 内容互动累计同步任务
 */
@Component
@JobExecutor(name = ContentInteractionSyncJob.JOB_NAME)
public class ContentInteractionSyncJob extends AbstractJobExecutor {

    private static final Logger log = LoggerFactory.getLogger(ContentInteractionSyncJob.class);
    public static final String JOB_NAME = "contentInteractionSyncJob";

    private final ContentInteractionSyncService contentInteractionSyncService;
    private final JacksonMapper jacksonMapper;

    public ContentInteractionSyncJob(ContentInteractionSyncService contentInteractionSyncService,
                                     JacksonMapper jacksonMapper) {
        this.contentInteractionSyncService = contentInteractionSyncService;
        this.jacksonMapper = jacksonMapper;
    }


    @Override
    protected ExecuteResult doJobExecute(JobArgs jobArgs) {
        log.info("{} param:{}", JOB_NAME, jacksonMapper.toJson(jobArgs));
        return contentInteractionSyncService.syncContent();
    }

}