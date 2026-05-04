package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.old.silence.bp.vo.PoetryUserStudySettingBpView;
import com.old.silence.content.api.PoetryUserStudySettingClient;
import com.old.silence.content.api.dto.PoetryUserStudySettingQuery;
import com.old.silence.content.domain.enums.StudyStatus;
import com.old.silence.job.client.core.IJobExecutor;
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
    public static final int PAGE_SIZE = 100;
    private final PoetryUserStudySettingClient poetryUserStudySettingClient;
    private final JacksonMapper jacksonMapper;

    public PoetryDailyStudyPlanJob(PoetryUserStudySettingClient poetryUserStudySettingClient,
                                   JacksonMapper jacksonMapper) {
        this.poetryUserStudySettingClient = poetryUserStudySettingClient;
        this.jacksonMapper = jacksonMapper;
    }

    @Override
    protected ExecuteResult doJobExecute(JobArgs jobArgs) {
        log.info("{} param:{}", POETRY_DAILY_STUDY_PLAN_JOB, jacksonMapper.toJson(jobArgs));

        var poetryUserStudySettingQuery = new PoetryUserStudySettingQuery();
        poetryUserStudySettingQuery.setStatus(StudyStatus.STUDYING);

        int pageNo = 0;
        int totalProcessed = 0;
        int totalSuccess = 0;
        int totalFailed = 0;

        while (true) {
            PageRequest pageRequest = PageRequest.of(pageNo, PAGE_SIZE);
            Page<PoetryUserStudySettingBpView> poetryUserStudySettingPage =
                    poetryUserStudySettingClient.query(poetryUserStudySettingQuery, pageRequest, PoetryUserStudySettingBpView.class);

            if (poetryUserStudySettingPage.isEmpty()) {
                break;
            }

            for (PoetryUserStudySettingBpView setting : poetryUserStudySettingPage.getContent()) {
                totalProcessed++;
                try {
                    poetryUserStudySettingClient.generateTodayPlan(setting.getUserId(), setting.getSubCategoryId());
                    totalSuccess++;
                } catch (Exception ex) {
                    totalFailed++;
                    log.error("{} process failed, settingId={}, userId={}, subCategoryId={}",
                            POETRY_DAILY_STUDY_PLAN_JOB, setting.getId(), setting.getUserId(), setting.getSubCategoryId(), ex);
                }
            }

            if (!poetryUserStudySettingPage.hasNext()) {
                break;
            }
            pageNo++;
        }

        String msg = String.format("处理完成 processed=%d, success=%d, failed=%d", totalProcessed, totalSuccess, totalFailed);
        log.info("{} {}", POETRY_DAILY_STUDY_PLAN_JOB, msg);
        return ExecuteResult.success(msg);
    }


}