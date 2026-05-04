package com.old.silence.content.domain.service;

import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.StudyMode;
import com.old.silence.content.infrastructure.persistence.DistributeStudyContentStrategy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moryzang
 */
@Component
public class DistributeBatchedStrategy implements DistributeStudyContentStrategy {

    @Override
    public StudyMode getDistributeStudyContentType() {
        return StudyMode.BATCHED;
    }

    /**
     * 分批分配策略：按固定批次大小分配
     */
    public List<List<BigInteger>> getDistributeStudyContentIds(List<BigInteger> items, int dailyCount) {
        List<List<BigInteger>> result = new ArrayList<>();
        int batchSize = Math.max(dailyCount / 2, 1); // 批次大小为每日数量的一半

        int index = 0;
        while (index < items.size()) {

            // 添加一个完整批次
            int batchEnd = Math.min(index + batchSize, items.size());
            List<BigInteger> dailyItems = new ArrayList<>(items.subList(index, batchEnd));
            index = batchEnd;

            // 如果还有空间，添加下一个批次的一部分
            if (dailyItems.size() < dailyCount && index < items.size()) {
                int remaining = dailyCount - dailyItems.size();
                int nextEnd = Math.min(index + remaining, items.size());
                dailyItems.addAll(items.subList(index, nextEnd));
                index = nextEnd;
            }

            result.add(dailyItems);
        }

        return result;
    }
}
