package com.old.silence.bp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.old.silence.bp.vo.PoetryUserStudySettingBpView;
import com.old.silence.content.api.PoetryUserStudySettingClient;
import com.old.silence.content.api.dto.PoetryUserStudySettingQuery;
import com.old.silence.content.domain.enums.StudyStatus;
import com.old.silence.job.common.client.dto.ExecuteResult;

/**
 * @author moryzang
 */
@Service
public class PoetryDailyStudyPlanService {

    public static final int PAGE_SIZE = 100;
    private static final Logger log = LoggerFactory.getLogger(PoetryDailyStudyPlanService.class);
    private final PoetryUserStudySettingClient poetryUserStudySettingClient;

    public PoetryDailyStudyPlanService(PoetryUserStudySettingClient poetryUserStudySettingClient) {
        this.poetryUserStudySettingClient = poetryUserStudySettingClient;
    }

    public ExecuteResult adjustPlan() {
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
                    log.error("{} process failed, settingId={}, userId={}, subCategoryId={}", setting.getId(), setting.getUserId(), setting.getSubCategoryId(), ex);
                }
            }

            if (!poetryUserStudySettingPage.hasNext()) {
                break;
            }
            pageNo++;
        }

        String msg = String.format("处理完成 processed=%d, success=%d, failed=%d", totalProcessed, totalSuccess, totalFailed);
        log.info("{}", msg);
        return ExecuteResult.success(msg);
    }
}
