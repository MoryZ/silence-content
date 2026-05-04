package com.old.silence.content.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.domain.model.poetry.PoetryUserLearningRecord;
import com.old.silence.content.domain.repository.poetry.PoetryDailyStudyPlanRepository;
import com.old.silence.content.domain.repository.poetry.PoetryUserLearningRecordRepository;
import com.old.silence.content.infrastructure.message.ContentMessages;
import com.old.silence.content.infrastructure.persistence.view.PoetryDailyStudyPlanOnlyCompletionRateView;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moryzang
 */
@Service
public class PoetryUserLearningRecordDomainService {

    private static final BigDecimal COMPLETED_RATE = BigDecimal.ONE;
    // 艾宾浩斯复习间隔（天）：第1次+1天，第2次+2天，第3次+4天，第4次+7天，第5次+15天，之后+30天
    private static final List<Long> EBBINGHAUS_INTERVALS = List.of(1L, 2L, 4L, 7L, 15L, 30L);
    private final PoetryUserLearningRecordRepository poetryUserLearningRecordRepository;
    private final PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository;
    private final PoetryStudyPlanAsyncService poetryStudyPlanAsyncService;
    private final JacksonMapper jacksonMapper;

    public PoetryUserLearningRecordDomainService(PoetryUserLearningRecordRepository poetryUserLearningRecordRepository,
                                                 PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository,
                                                 PoetryStudyPlanAsyncService poetryStudyPlanAsyncService,
                                                 JacksonMapper jacksonMapper) {
        this.poetryUserLearningRecordRepository = poetryUserLearningRecordRepository;
        this.poetryDailyStudyPlanRepository = poetryDailyStudyPlanRepository;
        this.poetryStudyPlanAsyncService = poetryStudyPlanAsyncService;
        this.jacksonMapper = jacksonMapper;
    }

    @Transactional
    public int completeLearningRecord(PoetryUserLearningRecord poetryUserLearningRecord) {
        var now = Instant.now();
        var existingRecord = poetryUserLearningRecordRepository.findByUserIdAndContentId(
                poetryUserLearningRecord.getUserId(), poetryUserLearningRecord.getContentId(),
                PoetryUserLearningRecord.class);

        int rowsAffected;
        if (existingRecord.isEmpty()) {
            // 首次学习
            poetryUserLearningRecord.setFirstStudiedAt(now);
            poetryUserLearningRecord.setReviewCount(0L);
            poetryUserLearningRecord.setLearningPhase(1L);
            poetryUserLearningRecord.setNextReviewAt(now.plusSeconds(EBBINGHAUS_INTERVALS.get(0) * 86400L));
            poetryUserLearningRecord.setMemoryStrength(new BigDecimal("1.0"));
            poetryUserLearningRecord.setEasinessFactor(new BigDecimal("2.5"));
            rowsAffected = poetryUserLearningRecordRepository.create(poetryUserLearningRecord);
        } else {
            // 复习
            var record = existingRecord.get();
            record.setStudyDuration(poetryUserLearningRecord.getStudyDuration());
            record.setRemembered(poetryUserLearningRecord.getRemembered());
            record.setLastReviewedAt(now);
            long newReviewCount = record.getReviewCount() + 1;
            record.setReviewCount(newReviewCount);
            if (Boolean.FALSE.equals(record.getRemembered())) {
                // 未记住：重置到明天再复习
                record.setNextReviewAt(now.plusSeconds(86400L));
            } else {
                long intervalDays = EBBINGHAUS_INTERVALS.get((int) Math.min(newReviewCount, EBBINGHAUS_INTERVALS.size() - 1));
                record.setNextReviewAt(now.plusSeconds(intervalDays * 86400L));
            }
            rowsAffected = poetryUserLearningRecordRepository.update(record);
            poetryUserLearningRecord = record;
        }

        // 查今日学习计划，更新新内容完成进度
        var poetryDailyStudyPlanOptional = poetryDailyStudyPlanRepository.findByUserIdAndSubCategoryIdAndPlanDate(
                poetryUserLearningRecord.getUserId(), poetryUserLearningRecord.getSubCategoryId(),
                LocalDate.now(), PoetryDailyStudyPlanOnlyCompletionRateView.class);

        if (poetryDailyStudyPlanOptional.isEmpty()) {
            throw ContentMessages.STUDY_PLAN_NOT_EXIST.createException();
        }
        var poetryDailyStudyPlan = poetryDailyStudyPlanOptional.get();

        // 仅当内容在今日新内容列表中时，才更新完成率
        var newItemIds = poetryDailyStudyPlan.getNewItemIds();
        if (newItemIds != null && !newItemIds.isBlank()) {
            var todayNewItems = jacksonMapper.fromCollectionJson(newItemIds, BigInteger.class);
            if (todayNewItems.contains(poetryUserLearningRecord.getContentId())) {
                var completeNewItems = poetryDailyStudyPlan.getCompletedNewItems();
                var updatedCompleteNewItems = (completeNewItems == null || completeNewItems.isBlank())
                        ? new ArrayList<BigInteger>()
                        : jacksonMapper.fromCollectionJson(completeNewItems, BigInteger.class);
                if (!updatedCompleteNewItems.contains(poetryUserLearningRecord.getContentId())) {
                    updatedCompleteNewItems.add(poetryUserLearningRecord.getContentId());
                }
                var updatedCompleteRate = BigDecimal.valueOf(CollectionUtils.size(updatedCompleteNewItems))
                        .divide(BigDecimal.valueOf(CollectionUtils.size(todayNewItems)), 4, RoundingMode.HALF_UP)
                        .setScale(2, RoundingMode.HALF_UP);
                poetryDailyStudyPlanRepository.updateCompletedNewItemsAndCompletionRate(
                        jacksonMapper.toJson(updatedCompleteNewItems), updatedCompleteRate, poetryDailyStudyPlan.getId());

                if (updatedCompleteRate.compareTo(COMPLETED_RATE) >= 0) {
                    generateTomorrowPlanAsync(poetryUserLearningRecord);
                }
            }
        }

        return rowsAffected;
    }

    private void generateTomorrowPlanAsync(PoetryUserLearningRecord poetryUserLearningRecord) {
        var userId = poetryUserLearningRecord.getUserId();
        var subCategoryId = poetryUserLearningRecord.getSubCategoryId();
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    poetryStudyPlanAsyncService.generateTomorrowPlan(userId, subCategoryId);
                }
            });
            return;
        }

        poetryStudyPlanAsyncService.generateTomorrowPlan(userId, subCategoryId);
    }
}
