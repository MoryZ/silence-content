package com.old.silence.content.domain.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.domain.enums.DailyStudyPlanStatus;
import com.old.silence.content.domain.enums.DistributeStudyContentType;
import com.old.silence.content.domain.model.PoetryDailyStudyPlan;
import com.old.silence.content.domain.repository.PoetryDailyStudyPlanRepository;
import com.old.silence.content.domain.service.factory.DistributeStudyContentFactory;
import com.old.silence.content.infrastructure.persistence.view.PoetryDailyStudyPlanOnlyCompletionRateView;
import com.old.silence.content.infrastructure.persistence.view.PoetryDailyStudyPlanOnlyNewItemIdsView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author moryzang
 */
@Service
public class PoetryDailyStudyPlanDomainService {


    private static final Logger log = LoggerFactory.getLogger(PoetryDailyStudyPlanDomainService.class);
    private final DistributeStudyContentFactory distributeStudyContentFactory;
    private final PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository;
    private final JacksonMapper jacksonMapper;

    public PoetryDailyStudyPlanDomainService(DistributeStudyContentFactory distributeStudyContentFactory, JacksonMapper jacksonMapper,
                                             PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository) {
        this.distributeStudyContentFactory = distributeStudyContentFactory;
        this.jacksonMapper = jacksonMapper;
        this.poetryDailyStudyPlanRepository = poetryDailyStudyPlanRepository;
    }

    public void adjustPoetryUserStudyPlan(List<BigInteger> poetryLearningContents, BigInteger userId,
                                          BigInteger subCategoryId, Long dailyNewItems) {


        var poetryDailyStudyPlans = generateNewPlans(userId, subCategoryId, dailyNewItems, poetryLearningContents);

        //创建计划
        poetryDailyStudyPlanRepository.bulkCreate(poetryDailyStudyPlans);

    }

    public void updatePoetryUserStudyPlan(List<BigInteger> poetryLearningContents, BigInteger userId, BigInteger subCategoryId, Long newDailyTarget) {
        DailyStudyPlanStatus currentStatus = getCurrentPlanStatus(userId, subCategoryId);

        var adjustDate = LocalDate.now();
        List<PoetryDailyStudyPlan> poetryDailyStudyPlans;
        // 2. 根据状态执行不同的调整策略
        switch (currentStatus) {
            case DailyStudyPlanStatus.NOT_STARTED ->
                    poetryDailyStudyPlans = handleNotStarted(userId, adjustDate, newDailyTarget, poetryLearningContents, subCategoryId);
            case DailyStudyPlanStatus.IN_PROGRESS ->
                    poetryDailyStudyPlans = handleInProgress(userId, adjustDate, newDailyTarget, poetryLearningContents, subCategoryId);
            case DailyStudyPlanStatus.COMPLETED ->
                    poetryDailyStudyPlans = handleCompleted(userId, adjustDate, newDailyTarget, poetryLearningContents, subCategoryId);
            default -> throw new IllegalStateException("未知的计划状态");
        }

        // 删除 今天和 以后的计划
        poetryDailyStudyPlanRepository.deleteByUserIdAndSubCategoryAndPlanDateGreaterThanEqual(userId, subCategoryId, adjustDate);

        //重新创建计划
        poetryDailyStudyPlanRepository.bulkCreate(poetryDailyStudyPlans);

    }

    private DailyStudyPlanStatus getCurrentPlanStatus(BigInteger userId, BigInteger subCategoryId) {
        var poetryDailyStudyPlan = getTodayPoetryDailyStudyPlan(userId, subCategoryId,
                PoetryDailyStudyPlanOnlyCompletionRateView.class);
        if (BigDecimal.ZERO.compareTo(poetryDailyStudyPlan.getCompletionRate()) == 0) {
            return DailyStudyPlanStatus.NOT_STARTED;
        } else if (BigDecimal.ONE.compareTo(poetryDailyStudyPlan.getCompletionRate()) == 0) {
            return DailyStudyPlanStatus.COMPLETED;
        }
        return DailyStudyPlanStatus.IN_PROGRESS;
    }

    private <T> T getTodayPoetryDailyStudyPlan(BigInteger userId, BigInteger subCategoryId, Class<T> projectionType) {
        return poetryDailyStudyPlanRepository.findByUserIdAndSubCategoryIdAndPlanDate(userId, subCategoryId, LocalDate.now(), projectionType)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * 状态1：当天任务未开始
     */
    private List<PoetryDailyStudyPlan> handleNotStarted(BigInteger userId,
                                                        LocalDate adjustDate,
                                                        Long newDailyTarget,
                                                        List<BigInteger> totalItems,
                                                        BigInteger subCategoryId) {

        log.info("用户 {} 在 {} 的任务未开始，直接应用新目标: {}", userId, adjustDate, newDailyTarget);

        // 重新计算剩余计划
        return recalculateRemainingPlans(userId, subCategoryId, LocalDate.now(), newDailyTarget, totalItems);
    }

    /**
     * 状态2：当天任务已开始，未完成
     */
    private List<PoetryDailyStudyPlan> handleInProgress(BigInteger userId,
                                                        LocalDate adjustDate,
                                                        Long newDailyTarget,
                                                        List<BigInteger> totalItems,
                                                        BigInteger subCategoryId) {
        PoetryDailyStudyPlanOnlyNewItemIdsView todayPlan = getTodayPoetryDailyStudyPlan(userId, subCategoryId, PoetryDailyStudyPlanOnlyNewItemIdsView.class);
        var originalTarget = getOriginalTarget(todayPlan, PoetryDailyStudyPlanOnlyNewItemIdsView::getNewItemIds);
        var completedCount = getOriginalTarget(todayPlan, PoetryDailyStudyPlanOnlyNewItemIdsView::getCompleteNewItems);
        log.info("用户 {} 在 {} 的任务进行中，已完成: {}，原目标: {}，新目标: {}",
                userId, adjustDate, completedCount, originalTarget, newDailyTarget);

        // 补差策略：先实现原目标，再补充差额
        int additionalTarget = newDailyTarget.intValue() - originalTarget;

        if (additionalTarget > 0) {
            // 需要补充学习
            List<BigInteger> additionalItemIds = getAdditionalItemIds(totalItems).getFirst();

            // 合并到今天的计划中
            List<BigInteger> todayTotalNewItems = mergeItemLists(todayPlan.getNewItemIds(), additionalItemIds);

            var completionRate = BigDecimal.valueOf(completedCount)
                    .divide(BigDecimal.valueOf(todayTotalNewItems.size()), 4, RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);
            // 修改完成率,修改今日要学习内容
            poetryDailyStudyPlanRepository.updateNewItemIdsAndCompletionRate(jacksonMapper.toJson(todayTotalNewItems), completionRate, todayPlan.getId());

            log.info("已为今天计划补充 {} 个新学习项目", additionalTarget);
        }

        return recalculateRemainingPlans(userId, subCategoryId, LocalDate.now(), newDailyTarget, totalItems);

    }

    /**
     * 状态3：当天任务已完成
     */
    private List<PoetryDailyStudyPlan> handleCompleted(BigInteger userId,
                                                       LocalDate adjustDate,
                                                       Long newDailyTarget,
                                                       List<BigInteger> totalItems,
                                                       BigInteger subCategoryId) {

        PoetryDailyStudyPlanOnlyNewItemIdsView todayPlan = getTodayPoetryDailyStudyPlan(userId, subCategoryId, PoetryDailyStudyPlanOnlyNewItemIdsView.class);
        var originalTarget = getOriginalTarget(todayPlan, PoetryDailyStudyPlanOnlyNewItemIdsView::getNewItemIds);
        var completedCount = getOriginalTarget(todayPlan, PoetryDailyStudyPlanOnlyNewItemIdsView::getCompleteNewItems);
        int additionalTarget = newDailyTarget.intValue() - originalTarget;

        log.info("用户 {} 在 {} 的任务已完成，原目标: {}，需要补充: {}",
                userId, adjustDate, originalTarget, additionalTarget);

        if (additionalTarget > 0) {
            // 提前透支：立即追加学习
            List<BigInteger> additionalItemIds = getAdditionalItemIds(totalItems).getFirst();

            // 合并到今天的计划中
            List<BigInteger> todayTotalNewItems = mergeItemLists(todayPlan.getNewItemIds(), additionalItemIds);

            // 创建额外的学习会话或更新今日计划
            var completionRate = BigDecimal.valueOf(completedCount)
                    .divide(BigDecimal.valueOf(todayTotalNewItems.size()), 4, RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);
            // 修改完成率,修改今日要学习内容
            poetryDailyStudyPlanRepository.updateNewItemIdsAndCompletionRate(jacksonMapper.toJson(todayTotalNewItems), completionRate, todayPlan.getId());

            log.info("已创建额外学习会话，补充 {} 个项目", additionalTarget);
        }

        return recalculateRemainingPlans(userId, subCategoryId, adjustDate.plusDays(1), newDailyTarget, totalItems);

    }

    private int getOriginalTarget(PoetryDailyStudyPlanOnlyNewItemIdsView todayPlan, Function<PoetryDailyStudyPlanOnlyNewItemIdsView, String> fieldGetter) {
        return StringUtils.isNotBlank(fieldGetter.apply(todayPlan)) ?
                CollectionUtils.size(jacksonMapper.fromCollectionJson(fieldGetter.apply(todayPlan), BigInteger.class))
                : 0;
    }

    /**
     * 合并老计划和新补充的学习内容
     * @param newItemIds 老计划的学习内容
     * @param additionalItemIds 需要补充的学习内容
     * @return 今日需要学习的内容
     */
    private List<BigInteger> mergeItemLists(String newItemIds, List<BigInteger> additionalItemIds) {
        var todayNewItemIds = jacksonMapper.fromCollectionJson(newItemIds, BigInteger.class);
        todayNewItemIds.addAll(additionalItemIds);
        return todayNewItemIds;

    }

    /**
     * 获取额外的学习会话或更新今日计划
     */
    private List<List<BigInteger>> getAdditionalItemIds(List<BigInteger> totalItemIds) {
        var distributeStudyContentStrategy = distributeStudyContentFactory.getDistributeStudyContentStrategy(DistributeStudyContentType.SEQUENTIAL);
        return distributeStudyContentStrategy.getDistributeStudyContentIds(totalItemIds, 1);
    }

    /**
     * 重新计算剩余的所有计划
     */
    private List<PoetryDailyStudyPlan> recalculateRemainingPlans(BigInteger userId,
                                                                 BigInteger subCategoryId,
                                                                 LocalDate adjustDate,
                                                                 Long newDailyTarget,
                                                                 List<BigInteger> totalItems) {

        // 计算已学习总量
        List<PoetryDailyStudyPlanOnlyNewItemIdsView> totalStudyPlans = getTotalLearnedCount(userId, subCategoryId, adjustDate);

        // 计算剩余项目
        List<BigInteger> remainingItems = getRemainingItems(totalItems, totalStudyPlans);

        // 计算需要的新天数（向上取整）
        int remainingDays = (int) Math.ceil((double) remainingItems.size() / newDailyTarget);

        log.info("重新计算计划: 已学 {}, 剩余 {}, 需要 {} 天", CollectionUtils.size(totalStudyPlans),
                remainingItems.size(), remainingDays);

        // 生成新的计划
        return generateNewPlans(userId, subCategoryId, newDailyTarget, remainingItems);
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
        Set<BigInteger> allCompletedItems = getStudyPlanItems(totalStudyContentIds, PoetryDailyStudyPlanOnlyNewItemIdsView::getCompleteNewItems);

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
     * 生成新的计划
     */
    private List<PoetryDailyStudyPlan> generateNewPlans(BigInteger userId, BigInteger subCategoryId, Long dailyNewItems, List<BigInteger> poetryLearningContentIds) {
        var distributeStudyContentStrategy = distributeStudyContentFactory.getDistributeStudyContentStrategy(DistributeStudyContentType.SEQUENTIAL);
        var distributeStudyContentIds = distributeStudyContentStrategy.getDistributeStudyContentIds(poetryLearningContentIds, dailyNewItems.intValue());

        return IntStream.range(0, distributeStudyContentIds.size())
                .mapToObj(i -> {
                    PoetryDailyStudyPlan plan = new PoetryDailyStudyPlan();
                    plan.setUserId(userId);
                    plan.setSubCategoryId(subCategoryId);

                    var newItemIds = distributeStudyContentIds.get(i);
                    plan.setNewItemIds(jacksonMapper.toJson(newItemIds));

                    var emptyListStr = jacksonMapper.toJson(List.of());
                    plan.setReviewItemIds(emptyListStr);
                    plan.setCompletedNewItems(emptyListStr);
                    plan.setCompletedReviewItems(emptyListStr);
                    plan.setPlanDate(LocalDate.now().plusDays(i));

                    return plan;
                })
                .collect(Collectors.toList());
    }

}
