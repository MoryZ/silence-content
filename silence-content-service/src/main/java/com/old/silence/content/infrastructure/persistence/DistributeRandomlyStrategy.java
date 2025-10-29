package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.old.silence.content.domain.enums.DistributeStudyContentType;

/**
 * @author moryzang
 */
public class DistributeRandomlyStrategy implements DistributeStudyContentStrategy {

    @Override
    public DistributeStudyContentType getDistributeStudyContentType() {
        return DistributeStudyContentType.RANDOM;
    }

    /**
     * 随机分配策略：随机打乱后分配
     */
    public List<List<BigInteger>> getDistributeStudyContentIds(List<BigInteger> items, int dailyCount) {
        List<BigInteger> shuffled = new ArrayList<>(items);
        Collections.shuffle(shuffled); // 随机打乱顺序

        return distributeSequentially(shuffled, dailyCount);
    }
}
