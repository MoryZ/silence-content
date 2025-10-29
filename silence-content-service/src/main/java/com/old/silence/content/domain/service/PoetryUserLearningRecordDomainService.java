package com.old.silence.content.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.domain.model.PoetryUserLearningRecord;
import com.old.silence.content.domain.repository.PoetryDailyStudyPlanRepository;
import com.old.silence.content.domain.repository.PoetryUserLearningRecordRepository;
import com.old.silence.content.infrastructure.message.ContentMessages;
import com.old.silence.content.infrastructure.persistence.view.PoetryDailyStudyPlanOnlyCompletionRateView;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author moryzang
 */
@Service
public class PoetryUserLearningRecordDomainService {

    private final PoetryUserLearningRecordRepository poetryUserLearningRecordRepository;
    private final PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository;
    private final JacksonMapper jacksonMapper;

    public PoetryUserLearningRecordDomainService(PoetryUserLearningRecordRepository poetryUserLearningRecordRepository,
                                                 PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository, JacksonMapper jacksonMapper) {
        this.poetryUserLearningRecordRepository = poetryUserLearningRecordRepository;
        this.poetryDailyStudyPlanRepository = poetryDailyStudyPlanRepository;
        this.jacksonMapper = jacksonMapper;
    }

    @Transactional
    public int create(PoetryUserLearningRecord poetryUserLearningRecord) {
        // 先保存学习记录
        var rowsAffected =  poetryUserLearningRecordRepository.create(poetryUserLearningRecord);

        // 查到今日学习计划
        var poetryDailyStudyPlanOptional = poetryDailyStudyPlanRepository.findByUserIdAndSubCategoryIdAndPlanDate(poetryUserLearningRecord.getUserId(), poetryUserLearningRecord.getSubCategoryId(),
                LocalDate.now(), PoetryDailyStudyPlanOnlyCompletionRateView.class);

        if (poetryDailyStudyPlanOptional.isEmpty()) {
            throw ContentMessages.STUDY_PLAN_NOT_EXIST.createException();
        }
        var poetryDailyStudyPlan = poetryDailyStudyPlanOptional.get();

        var completeNewItems = poetryDailyStudyPlan.getCompletedNewItems();

        var updatedCompleteNewItems = completeNewItems.isEmpty() ? new ArrayList<BigInteger>() : jacksonMapper.fromCollectionJson(completeNewItems, BigInteger.class);
        updatedCompleteNewItems.add(poetryUserLearningRecord.getContentId());


        var newItemIds = poetryDailyStudyPlan.getNewItemIds();
        var todayTotalNewItemsSize = newItemIds.isEmpty() ? 0 : CollectionUtils.size(jacksonMapper.fromCollectionJson(newItemIds, BigInteger.class));
        var updatedCompleteRate  =
                BigDecimal.valueOf(CollectionUtils.size(updatedCompleteNewItems))
                        .divide(BigDecimal.valueOf(todayTotalNewItemsSize), 4, RoundingMode.HALF_UP)
                        .setScale(2, RoundingMode.HALF_UP);

        // 更新已学习内容和完成率
        poetryDailyStudyPlanRepository.updateCompletedNewItemsAndCompletionRate(jacksonMapper.toJson(updatedCompleteNewItems), updatedCompleteRate, poetryDailyStudyPlan.getId());

        return rowsAffected;

    }
}
