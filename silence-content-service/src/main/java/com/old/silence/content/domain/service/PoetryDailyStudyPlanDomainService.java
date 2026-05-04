package com.old.silence.content.domain.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.domain.enums.StudyMode;
import com.old.silence.content.domain.enums.StudyStatus;
import com.old.silence.content.domain.model.poetry.PoetryDailyStudyPlan;
import com.old.silence.content.domain.model.poetry.PoetryUserLearningRecord;
import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;
import com.old.silence.content.domain.repository.poetry.PoetryDailyStudyPlanRepository;
import com.old.silence.content.domain.repository.poetry.PoetryUserLearningRecordRepository;
import com.old.silence.content.domain.repository.poetry.PoetryUserStudySettingRepository;
import com.old.silence.content.domain.service.factory.DistributeStudyContentFactory;
import com.old.silence.content.infrastructure.persistence.view.PoetryDailyStudyPlanOnlyNewItemIdsView;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author moryzang
 */
@Service
public class PoetryDailyStudyPlanDomainService {


    private static final Logger log = LoggerFactory.getLogger(PoetryDailyStudyPlanDomainService.class);
    private final DistributeStudyContentFactory distributeStudyContentFactory;
    private final PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository;
    private final PoetryUserLearningRecordRepository poetryUserLearningRecordRepository;
    private final PoetryUserStudySettingRepository poetryUserStudySettingRepository;
    private final JacksonMapper jacksonMapper;

    public PoetryDailyStudyPlanDomainService(DistributeStudyContentFactory distributeStudyContentFactory, JacksonMapper jacksonMapper,
                                             PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository,
                                             PoetryUserLearningRecordRepository poetryUserLearningRecordRepository,
                                             PoetryUserStudySettingRepository poetryUserStudySettingRepository) {
        this.distributeStudyContentFactory = distributeStudyContentFactory;
        this.jacksonMapper = jacksonMapper;
        this.poetryDailyStudyPlanRepository = poetryDailyStudyPlanRepository;
        this.poetryUserLearningRecordRepository = poetryUserLearningRecordRepository;
        this.poetryUserStudySettingRepository = poetryUserStudySettingRepository;
    }

    public void createStudyPlan(List<BigInteger> poetryLearningContents, BigInteger userId,
                                BigInteger subCategoryId, StudyMode studyMode, Long dailyNewItems) {
        ensurePlanOnDate(poetryLearningContents, userId, subCategoryId, studyMode, dailyNewItems, LocalDate.now());
    }

    public void updatePoetryUserStudyPlan(List<BigInteger> poetryLearningContents, BigInteger userId, BigInteger subCategoryId,
                                          StudyMode studyMode, Long newDailyTarget) {
        var tomorrow = LocalDate.now().plusDays(1);
        poetryDailyStudyPlanRepository.deleteByUserIdAndSubCategoryAndPlanDateGreaterThanEqual(userId, subCategoryId, tomorrow);
        ensurePlanOnDate(poetryLearningContents, userId, subCategoryId, studyMode, newDailyTarget, tomorrow);

    }

    public void ensurePlanOnDate(List<BigInteger> totalItems,
                                 BigInteger userId,
                                 BigInteger subCategoryId,
                                 StudyMode studyMode,
                                 Long dailyNewItems,
                                 LocalDate planDate) {
        var existingTodayPlan = poetryDailyStudyPlanRepository.existsByUserIdAndSubCategoryIdAndPlanDate(userId, subCategoryId, planDate);
        if (existingTodayPlan) {
            return;
        }

        List<PoetryDailyStudyPlanOnlyNewItemIdsView> historicalPlans = getTotalLearnedCount(userId, subCategoryId, planDate);
        List<BigInteger> remainingItems = getRemainingItems(totalItems, historicalPlans);
        if (CollectionUtils.isEmpty(remainingItems)) {
            markStudyCompleted(userId, subCategoryId);
            log.info("用户 {} 分类 {} 在 {} 无剩余学习内容，无需创建计划", userId, subCategoryId, planDate);
            return;
        }

        // 查询到期复习项（nextReviewAt <= planDate 结束）
        Instant reviewCutoff = planDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        List<BigInteger> reviewItemIds = poetryUserLearningRecordRepository
                .findByUserIdAndSubCategoryIdAndNextReviewAtLessThanEqual(userId, subCategoryId, reviewCutoff, PoetryUserLearningRecord.class)
                .stream()
                .map(PoetryUserLearningRecord::getContentId)
                .toList();

        var studyPlan = generateSingleDayPlan(userId, subCategoryId, studyMode, planDate, dailyNewItems, remainingItems, reviewItemIds);
        poetryDailyStudyPlanRepository.create(studyPlan);
        log.info("已为用户 {} 分类 {} 创建 {} 的学习计划", userId, subCategoryId, planDate);
    }

    private void markStudyCompleted(BigInteger userId, BigInteger subCategoryId) {
        var settingOptional = poetryUserStudySettingRepository.findBySubCategoryIdAndUserId(subCategoryId, userId, PoetryUserStudySetting.class);
        if (settingOptional.isEmpty()) {
            return;
        }

        var setting = settingOptional.get();
        int affectedRows = poetryUserStudySettingRepository.updateStatusByIdAndStatus(
                StudyStatus.COMPLETED,
                setting.getId(),
                StudyStatus.STUDYING);
        if (affectedRows > 0) {
            log.info("用户 {} 分类 {} 学习状态更新为已学完", userId, subCategoryId);
        }
    }

    /**
     * 获取剩余未学习内容（包含之前未学完的）
     */
    public List<BigInteger> getRemainingItems(List<BigInteger> totalItems,
                                              List<PoetryDailyStudyPlanOnlyNewItemIdsView> totalStudyContentIds) {
        if (totalItems == null || totalItems.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取所有已学习的内容（包括未完成的）
        Set<BigInteger> allAttemptedItems = getStudyPlanItems(totalStudyContentIds, PoetryDailyStudyPlanOnlyNewItemIdsView::getNewItemIds);

        // 获取所有已完成的内容
        Set<BigInteger> allCompletedItems = getStudyPlanItems(totalStudyContentIds, PoetryDailyStudyPlanOnlyNewItemIdsView::getCompletedNewItems);

        // 剩余内容 = 总内容 - 已完成内容
        List<BigInteger> remainingItems = totalItems.stream()
                .filter(item -> !allCompletedItems.contains(item))
                .collect(Collectors.toList());

        // 特别标记之前未学完的内容
        Set<BigInteger> unfinishedItems = allAttemptedItems.stream()
                .filter(item -> !allCompletedItems.contains(item))
                .collect(Collectors.toSet());

        log.info("进度统计: 总项目={}, 已尝试={}, 已完成={}, 未完成={}, 剩余={}",
                totalItems.size(), allAttemptedItems.size(), allCompletedItems.size(),
                unfinishedItems.size(), remainingItems.size());

        return remainingItems;
    }

    /**
     * 获取历史计划中已完成的学习内容
     */
    private Set<BigInteger> getStudyPlanItems(List<PoetryDailyStudyPlanOnlyNewItemIdsView> historicalPlans,
                                              Function<PoetryDailyStudyPlanOnlyNewItemIdsView, String> fieldGetter) {
        Set<BigInteger> attemptedItems = new HashSet<>();

        for (PoetryDailyStudyPlanOnlyNewItemIdsView plan : historicalPlans) {
            // 计划学习的内容（无论是否完成）
            if (StringUtils.isNotBlank(fieldGetter.apply(plan))) {
                List<BigInteger> items = jacksonMapper.fromCollectionJson(fieldGetter.apply(plan), BigInteger.class);
                attemptedItems.addAll(items);
            }
        }
        return attemptedItems;
    }


    /**
     * 获取所有已尝试学习的内容（包括未完成的）
     */
    private List<PoetryDailyStudyPlanOnlyNewItemIdsView> getTotalLearnedCount(BigInteger userId, BigInteger subCategoryId, LocalDate planDate) {
        return poetryDailyStudyPlanRepository.findByUserIdAndSubCategoryIdAndPlanDateLessThan(
                userId, subCategoryId, planDate, PoetryDailyStudyPlanOnlyNewItemIdsView.class
        );

    }

    /**
     * 生成单日计划
     */
    private PoetryDailyStudyPlan generateSingleDayPlan(BigInteger userId,
                                                       BigInteger subCategoryId,
                                                       StudyMode studyMode,
                                                       LocalDate planDate,
                                                       Long dailyNewItems,
                                                       List<BigInteger> poetryLearningContentIds,
                                                       List<BigInteger> reviewItemIds) {
        var distributeStudyContentStrategy = distributeStudyContentFactory.getDistributeStudyContentStrategy(studyMode);
        var distributeStudyContentIds = distributeStudyContentStrategy.getDistributeStudyContentIds(poetryLearningContentIds, dailyNewItems.intValue());
        var newItemIds = distributeStudyContentIds.getFirst();

        PoetryDailyStudyPlan plan = new PoetryDailyStudyPlan();
        plan.setUserId(userId);
        plan.setSubCategoryId(subCategoryId);
        plan.setNewItemIds(jacksonMapper.toJson(newItemIds));
        plan.setReviewItemIds(jacksonMapper.toJson(reviewItemIds));

        var emptyListStr = jacksonMapper.toJson(List.of());
        plan.setCompletedNewItems(emptyListStr);
        plan.setCompletedReviewItems(emptyListStr);
        plan.setPlanDate(planDate);
        return plan;
    }

}
