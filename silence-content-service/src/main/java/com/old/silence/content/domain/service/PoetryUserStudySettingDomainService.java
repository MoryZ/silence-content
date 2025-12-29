package com.old.silence.content.domain.service;

import org.springframework.stereotype.Service;
import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;
import com.old.silence.content.domain.repository.poetry.PoetryLearningContentRepository;
import com.old.silence.content.domain.repository.poetry.PoetryUserStudySettingRepository;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.commons.domain.BigIdOnlyView;

/**
 * @author moryzang
 */
@Service
public class PoetryUserStudySettingDomainService {

    private final PoetryDailyStudyPlanDomainService poetryDailyStudyPlanDomainService;
    private final PoetryUserStudySettingRepository poetryUserStudySettingRepository;
    private final PoetryLearningContentRepository poetryLearningContentRepository;

    public PoetryUserStudySettingDomainService(PoetryDailyStudyPlanDomainService poetryDailyStudyPlanDomainService,
                                               PoetryUserStudySettingRepository poetryUserStudySettingRepository,
                                               PoetryLearningContentRepository poetryLearningContentRepository) {
        this.poetryDailyStudyPlanDomainService = poetryDailyStudyPlanDomainService;
        this.poetryUserStudySettingRepository = poetryUserStudySettingRepository;
        this.poetryLearningContentRepository = poetryLearningContentRepository;
    }

    public int create(PoetryUserStudySetting poetryUserStudySetting) {
        var rowsAffected = poetryUserStudySettingRepository.create(poetryUserStudySetting);

        // 查询一共多少个内容
        var poetryLearningContents = poetryLearningContentRepository.findByGradeIdAndSubCategoryId(poetryUserStudySetting.getGradeId(), poetryUserStudySetting.getSubCategoryId(),
                BigIdOnlyView.class);
        var poetryLearningContentIds = CollectionUtils.transformToList(poetryLearningContents, BigIdOnlyView::getId);
        // 调整计划
        poetryDailyStudyPlanDomainService.adjustPoetryUserStudyPlan(poetryLearningContentIds, poetryUserStudySetting.getUserId(), poetryUserStudySetting.getSubCategoryId(),
                poetryUserStudySetting.getDailyNewItems());

        return rowsAffected;
    }

    public int update(PoetryUserStudySetting poetryUserStudySetting) {
        var rowsAffected = poetryUserStudySettingRepository.update(poetryUserStudySetting);

        // 查询一共多少个内容
        var poetryLearningContents = poetryLearningContentRepository.findByGradeIdAndSubCategoryId(poetryUserStudySetting.getGradeId(), poetryUserStudySetting.getSubCategoryId(),
                BigIdOnlyView.class);
        var poetryLearningContentIds = CollectionUtils.transformToList(poetryLearningContents, BigIdOnlyView::getId);

        // 调整计划
        poetryDailyStudyPlanDomainService.updatePoetryUserStudyPlan(poetryLearningContentIds, poetryUserStudySetting.getUserId(), poetryUserStudySetting.getSubCategoryId(),
                poetryUserStudySetting.getDailyNewItems());
        return rowsAffected;
    }
}
