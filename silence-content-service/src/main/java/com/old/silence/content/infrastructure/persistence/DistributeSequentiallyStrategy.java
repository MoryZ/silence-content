package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.old.silence.content.domain.enums.DistributeStudyContentType;

/**
 * @author moryzang
 */
public class DistributeSequentiallyStrategy implements DistributeStudyContentStrategy {

    @Override
    public DistributeStudyContentType getDistributeStudyContentType() {
        return DistributeStudyContentType.SEQUENTIAL;
    }

    /**
     * 顺序分配策略：按原始顺序依次分配
     */
    @Override
    public List<List<BigInteger>> getDistributeStudyContentIds(List<BigInteger> studyContentIds, int dailyCount) {
        return distributeSequentially(studyContentIds, dailyCount);
    }
}
