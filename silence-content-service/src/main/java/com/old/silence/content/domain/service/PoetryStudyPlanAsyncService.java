package com.old.silence.content.domain.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;
import com.old.silence.content.domain.repository.poetry.PoetryLearningContentRepository;
import com.old.silence.content.domain.repository.poetry.PoetryUserStudySettingRepository;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.commons.domain.BigIdOnlyView;

import java.math.BigInteger;
import java.time.LocalDate;

/**
 * @author moryzang
 */
@Service
public class PoetryStudyPlanAsyncService {

    private final PoetryDailyStudyPlanDomainService poetryDailyStudyPlanDomainService;
    private final PoetryUserStudySettingRepository poetryUserStudySettingRepository;
    private final PoetryLearningContentRepository poetryLearningContentRepository;

    public PoetryStudyPlanAsyncService(PoetryDailyStudyPlanDomainService poetryDailyStudyPlanDomainService,
                                       PoetryUserStudySettingRepository poetryUserStudySettingRepository,
                                       PoetryLearningContentRepository poetryLearningContentRepository) {
        this.poetryDailyStudyPlanDomainService = poetryDailyStudyPlanDomainService;
        this.poetryUserStudySettingRepository = poetryUserStudySettingRepository;
        this.poetryLearningContentRepository = poetryLearningContentRepository;
    }

    @Async("studyPlanTaskExecutor")
    public void generateTomorrowPlan(BigInteger userId, BigInteger subCategoryId) {
        var settingOptional = poetryUserStudySettingRepository.findBySubCategoryIdAndUserId(subCategoryId, userId, PoetryUserStudySetting.class);
        if (settingOptional.isEmpty()) {
            return;
        }

        var setting = settingOptional.get();
        var poetryLearningContents = poetryLearningContentRepository.findByGradeIdAndSubCategoryId(setting.getGradeId(), setting.getSubCategoryId(), BigIdOnlyView.class);
        var poetryLearningContentIds = CollectionUtils.transformToList(poetryLearningContents, BigIdOnlyView::getId);

        poetryDailyStudyPlanDomainService.ensurePlanOnDate(poetryLearningContentIds, setting.getUserId(), setting.getSubCategoryId(),
                setting.getStudyMode(), setting.getDailyNewCount(), LocalDate.now().plusDays(1));
    }
}