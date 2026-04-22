package com.old.silence.content.domain.service.tournament;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.dto.ExerciseSubmitItem;
import com.old.silence.content.api.tournament.tournament.dto.support.ExerciseScoreCoefficient;
import com.old.silence.core.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author moryzang
 */
@Service
public final class TournamentChallengeScoreDomainService {
    private static final Logger logger = LoggerFactory.getLogger(TournamentChallengeScoreDomainService.class);

    private TournamentChallengeScoreDomainService() {

    }

    /**
     * 计算个人挑战的基础得分
     * 公式：sum(每个运动项的 result × scoreRate)
     *
     * @param exerciseSubmitDetails 用户提交的运动记录列表
     * @param allowedItems 允许的运动项及其得分系数
     */
    //TODO 根据类型匹配不同的计算方式，暂不实现
    public static BigDecimal calculateBasicScore(List<ExerciseSubmitItem> exerciseSubmitDetails,
            List<ExerciseScoreCoefficient> allowedItems) {
        var coefficientMap = CollectionUtils.transformToMap(allowedItems, ExerciseScoreCoefficient::getCode, ExerciseScoreCoefficient::getScoreRate);
        BigDecimal totalScore = BigDecimal.ZERO;
        for (ExerciseSubmitItem item : exerciseSubmitDetails) {
            String type = item.getType();
            Integer result = item.getResult();
            BigDecimal coefficient = coefficientMap.get(type);
            BigDecimal itemScore = coefficient.multiply(BigDecimal.valueOf(result));
            totalScore = totalScore.add(itemScore);
            logger.debug("运动项: {}, 系数: {}, 时长或者个数: {}, 得分: {}", type, coefficient, result, itemScore);
        }
        totalScore = totalScore.setScale(2, RoundingMode.HALF_UP);
        logger.info("计算基础得分完成: {}", totalScore);
        return totalScore;
    }
}
