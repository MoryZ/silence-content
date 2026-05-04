package com.old.silence.content.infrastructure.persistence;

import com.old.silence.content.domain.enums.StudyMode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moryzang
 */
public interface DistributeStudyContentStrategy {

    StudyMode getDistributeStudyContentType();

    List<List<BigInteger>> getDistributeStudyContentIds(List<BigInteger> studyContentIds, int dailyCount);

    default List<List<BigInteger>> distributeSequentially(List<BigInteger> studyContentIds, int dailyCount) {
        List<List<BigInteger>> result = new ArrayList<>();
        int totalSize = studyContentIds.size();

        for (int i = 0; i < totalSize; i += dailyCount) {
            int end = Math.min(i + dailyCount, totalSize);
            result.add(new ArrayList<>(studyContentIds.subList(i, end)));
        }

        return result;
    }
}
