package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.old.silence.content.domain.enums.DistributeStudyContentType;

/**
 * @author moryzang
 */
public class DistributeInterleavedStrategy implements DistributeStudyContentStrategy {

    @Override
    public DistributeStudyContentType getDistributeStudyContentType() {
        return DistributeStudyContentType.INTERLEAVED;
    }

    /**
     * 交错分配策略：类似发牌的方式分配，有助于长期记忆
     * 例如：第1天：1,3,5,7... 第2天：2,4,6,8...
     */
    public List<List<BigInteger>> getDistributeStudyContentIds(List<BigInteger> items, int dailyCount) {
        List<List<BigInteger>> result = new ArrayList<>();
        int totalSize = items.size();
        int totalDays = (int) Math.ceil((double) totalSize / dailyCount);

        // 初始化每天的计划列表
        for (int i = 0; i < totalDays; i++) {
            result.add(new ArrayList<>());
        }

        // 交错分配
        int dayIndex = 0;
        for (int i = 0; i < totalSize; i++) {
            result.get(dayIndex).add(items.get(i));
            dayIndex = (dayIndex + 1) % totalDays;

            // 如果某天已经达到每日上限，就跳到下一天
            if (result.get(dayIndex).size() >= dailyCount) {
                dayIndex = findNextAvailableDay(result, dailyCount, dayIndex);
            }
        }

        return result;
    }

    /**
     * 查找下一个可用的天数（用于交错分配）
     */
    private int findNextAvailableDay(List<List<BigInteger>> plans, int dailyCount, int currentDay) {
        for (int i = 0; i < plans.size(); i++) {
            int checkDay = (currentDay + i) % plans.size();
            if (plans.get(checkDay).size() < dailyCount) {
                return checkDay;
            }
        }
        return 0;
    }
}
