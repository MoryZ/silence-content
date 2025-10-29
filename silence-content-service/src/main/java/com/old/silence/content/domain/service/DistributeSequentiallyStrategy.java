package com.old.silence.content.domain.service;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

import com.old.silence.content.domain.enums.DistributeStudyContentType;
import com.old.silence.content.infrastructure.persistence.DistributeStudyContentStrategy;

/**
 * @author moryzang
 */
@Component
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
