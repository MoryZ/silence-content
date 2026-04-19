package com.old.silence.content.console.api.validation;

import org.springframework.stereotype.Component;
import com.google.common.collect.Range;
import com.old.silence.content.api.tournament.tournament.dto.support.ExerciseScoreCoefficient;
import com.old.silence.content.api.tournament.tournament.dto.support.StageConfig;
import com.old.silence.content.api.tournament.tournament.dto.support.TierDefinition;
import com.old.silence.content.api.tournament.tournament.dto.support.TierMapping;
import com.old.silence.content.console.dto.EventGameRewardItemAndRuleConsoleCommand;
import com.old.silence.content.console.dto.TournamentConfigConsoleCommand;
import com.old.silence.content.console.enums.TimeCheckType;
import com.old.silence.content.console.message.MarketingMessages;
import com.old.silence.content.console.utils.TimeMergeUtils;
import com.old.silence.core.exception.SilenceException;
import com.old.silence.core.util.CollectionUtils;


import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TournamentConfigTimeValidator {


    /**
     * 校验时间和区间等参数
     * <p>
     * 1.活动开始时间<活动结束时间
     * <p>
     * 2.报名开始时间<报名结束时间 在活动时间内
     * <p>
     * 3.且比赛开始时间<比赛结束时间；
     * 需校验比赛时间在活动时间范围内；否则toast提示：比赛时间需在活动时间范围内
     * 需校验比赛开始时间在报名结束时间后，且两个时间的间隔时间必须大于3小时；
     * 否则toast提示：比赛开始时间和报名结束时间需要间隔至少3小时
     * <p>
     * 4.活动最小人数10人 最大人数 仅支持正整数，最大99999；且最小人数<最大人数
     * <p>
     * 5. 中奖开始区间<结束区间；仅支持输入正整数，最大99999
     * 中奖区间不可重叠，否则toast提示：中奖区间存在重叠，请检查
     * 中奖区间需连续（如配置区间为1-10、20-30），否则toast提示：中奖区间需连续，请检查
     * <p>
     * 6.不能有重复的运动项
     * <p>
     * 7.挑战 开始时间<结束时间；在比赛时间区间内，每日所选区间内可进行比赛；
     * a、挑战时间的结束时间需小于比赛结束时间（取时分秒对比），否则toast提示：挑战结束时间需在比赛结束时间前，请检查；
     * <p>
     * 8.周期段位 均为闭区间；暗提示为开始区间、结束区间；且开始区间<结束区间；仅支持输入正整数，最大99999；
     * <p>
     * 9.最终段位 均为闭区间；暗提示为开始区间、结束区间；且开始区间<结束区间；仅支持输入正整数，最大99999
     * 最大支持10行，最小保留1行
     */
    public void checkTournamentConfig(TournamentConfigConsoleCommand tournamentConfig) {

        if (tournamentConfig.getMarketingEvent() != null) {
            var marketingEventStartTime = tournamentConfig.getMarketingEvent().getStartTime();
            var marketingEventEndTime = tournamentConfig.getMarketingEvent().getStartTime();

            if (marketingEventStartTime.isAfter(marketingEventEndTime)) {
                throw new SilenceException(MarketingMessages.EVENT_GAME_START_TIME_LESS_THAN_END_TIME);
            }

            if (tournamentConfig.getRegistrationStartTime().isAfter(tournamentConfig.getRegistrationEndTime())) {
                throw new SilenceException(MarketingMessages.EVENT_REGISTRATION_START_TIME_LESS_THAN_END_TIME);
            }

            if (TimeMergeUtils.isOverFlowForInstant(marketingEventStartTime, marketingEventEndTime ,
                    List.of(Range.closed(tournamentConfig.getRegistrationStartTime(),
                            tournamentConfig.getRegistrationEndTime())))) {
                throw new SilenceException(MarketingMessages.EVENT_REGISTRATION_TIME_WITHIN_EVENT_TIME);

            }

            if (tournamentConfig.getTournamentStartTime().isAfter(tournamentConfig.getTournamentEndTime())) {
                // 比赛开始时间要在比赛结束时间之前
                throw new SilenceException(MarketingMessages.TOURNAMENT_GAME_START_TIME_LESS_THAN_END_TIME);

            } else {
                if (TimeMergeUtils.isOverFlowForInstant(marketingEventStartTime, marketingEventEndTime,
                        List.of(Range.closed(tournamentConfig.getRegistrationStartTime(),
                                tournamentConfig.getRegistrationEndTime())))) {
                    // 比赛时间需在活动时间范围内
                    throw new SilenceException(MarketingMessages.TOURNAMENT_TIME_WITHIN_EVENT_TIME);

                }

                if (tournamentConfig.getTournamentStartTime().isBefore(tournamentConfig.getRegistrationEndTime().plus(3, ChronoUnit.HOURS))) {
                    // 比赛开始时间要在报名结束时间3小时后
                    throw new SilenceException(MarketingMessages.REGISTRATION_END_TIME_AND_GAME_START_TIME_GAP);
                }
            }
        }

        if (tournamentConfig.getMinParticipants() >= tournamentConfig.getMaxParticipants()) {
            // 最小报名人数<最大报名人数
            throw new SilenceException(MarketingMessages.MIN_REGISTRATION_LESS_THAN_MAX);
        }

        if (CollectionUtils.isNotEmpty(tournamentConfig.getEventGameRewardItems())) {

            for (EventGameRewardItemAndRuleConsoleCommand eventGameRewardItem : tournamentConfig.getEventGameRewardItems()) {
                if (eventGameRewardItem.getEventGameRewardItemRule().getRangeStart() >= eventGameRewardItem.getEventGameRewardItemRule().getRangeEnd()) {
                    // 开始区间<结束区间
                    throw new SilenceException(MarketingMessages.PRIZE_DRAW_START_TIME_LESS_THAN_END_TIME);

                }
            }
            var checkRanges = CollectionUtils.transformToList(tournamentConfig.getEventGameRewardItems(),
                    eventGameRewardItemAndRule -> Range.closed(eventGameRewardItemAndRule.getEventGameRewardItemRule().getRangeStart(), eventGameRewardItemAndRule.getEventGameRewardItemRule().getRangeEnd()));
            var overlap = checkTimeIntervals(checkRanges, TimeCheckType.OVERLAP);
            // 1 有重叠
            if (overlap) {
                throw new SilenceException(MarketingMessages.PRIZE_DRAW_TIME_OVERLAP);
            }
            // 2 不连续
            var continuous = checkTimeIntervals(checkRanges, TimeCheckType.CONTINUOUS);
            if (continuous) {
                throw new SilenceException(MarketingMessages.PRIZE_DRAW_TIME_CONTINUOUS);
            }
        }

        if (tournamentConfig.getCycleStageConfig() != null) {
            if (CollectionUtils.isNotEmpty(tournamentConfig.getCycleStageConfig().getExerciseScoreCoefficients())) {
                var exerciseScoreCoefficients = tournamentConfig.getCycleStageConfig().getExerciseScoreCoefficients();
                if (exerciseScoreCoefficients.size() != new HashSet<>(CollectionUtils.transformToList(exerciseScoreCoefficients, ExerciseScoreCoefficient::getCode)).size()) {
                    // 不能有重复的运动项
                    throw new SilenceException(MarketingMessages.DUPLICATE_SPORT_ITEM);
                }
            }

            if (CollectionUtils.isNotEmpty(tournamentConfig.getCycleStageConfig().getStageConfigs())) {
                var stageConfigs = tournamentConfig.getCycleStageConfig().getStageConfigs();
                for (StageConfig stageConfig : stageConfigs) {
                    if (stageConfig.getStartTime().isAfter(stageConfig.getEndTime())) {
                        // 挑战开始时间<结束时间
                        throw new SilenceException(MarketingMessages.CHALLENGE_START_TIME_LESS_THAN_END_TIME);
                    }
                }

            }

            // 周期段位
            if (CollectionUtils.isNotEmpty(tournamentConfig.getCycleStageConfig().getCycleTierDefinitions())) {
                var tierMappings = CollectionUtils.transformToList(
                        tournamentConfig.getCycleStageConfig().getCycleTierDefinitions(), TierDefinition::getTierMapping);
                for (TierMapping tierMapping : tierMappings) {
                    if (tierMapping.getRankStart() >= tierMapping.getRankEnd()) {
                        // 段位开始<段位结束
                        throw new SilenceException(MarketingMessages.RANK_START_LESS_THAN_END);
                    }
                }
                var checkRanges = CollectionUtils.transformToList(tierMappings, tierMapping ->
                        Range.closed(tierMapping.getRankStart(), tierMapping.getRankEnd()));
                var overlap = checkTimeIntervals(checkRanges, TimeCheckType.OVERLAP);
                // 1 有重叠
                if (overlap) {
                    throw new SilenceException(MarketingMessages.RANK_INTERVAL_OVERLAP);
                }
                // 2 不连续
                var continuous = checkTimeIntervals(checkRanges, TimeCheckType.CONTINUOUS);
                if (continuous) {
                    throw new SilenceException(MarketingMessages.RANK_INTERVAL_CONTINUOUS);
                }

                // 3 周期段位区间最大值（段位结束区间-段位开始区间）*小组数量需要小于等于最大报名人数
                int actualSize = tournamentConfig.getGroupSize() * tierMappings.size();
                boolean isOverflow = actualSize > tournamentConfig.getMaxParticipants();
                if (isOverflow) {
                    throw new SilenceException(MarketingMessages.TEAM_SIZE_EXCEEDS_RANK_MAX);
                }
            }

            if (CollectionUtils.isNotEmpty(tournamentConfig.getCycleStageConfig().getTournamentTierDefinitions())) {
                var tierMappings = CollectionUtils.transformToList(
                        tournamentConfig.getCycleStageConfig().getTournamentTierDefinitions(), TierDefinition::getTierMapping);
                var checkRanges = CollectionUtils.transformToList(tierMappings, tierMapping ->
                        Range.closed(tierMapping.getRankStart(), tierMapping.getRankEnd()));
                var overlap = checkTimeIntervals(checkRanges, TimeCheckType.OVERLAP);
                // 1 有重叠
                if (overlap) {
                    throw new SilenceException(MarketingMessages.TOURNAMENT_RANK_INTERVAL_OVERLAP);
                }
                // 2 不连续
                var continuous = checkTimeIntervals(checkRanges, TimeCheckType.CONTINUOUS);
                if (continuous) {
                    throw new SilenceException(MarketingMessages.TOURNAMENT_RANK_INTERVAL_MUST_BE_CONTINUOUS);
                }

                // 3 晋级段位区间最大值（段位结束区间-段位开始区间）*小组数量需要小于等于最大报名人数
                int actualSize = tournamentConfig.getGroupSize() * tierMappings.size();
                boolean isOverflow = actualSize > tournamentConfig.getMaxParticipants();
                if (isOverflow) {
                    throw new SilenceException(MarketingMessages.TOURNAMENT_RANKS_MUST_BE_GREATER_THAN_OR_EQUAL_REGISTRATION_LIMIT);
                }
            }

        }
    }

    /**
     * 校验区间列表是否满足：不重叠、连续
     *
     * @param intervals 区间列表
     * @return true: 满足条件; false: 不满足
     */
    private static boolean checkTimeIntervals(List<Range<Integer>> intervals, TimeCheckType timeCheckType) {

        // 1. 按start排序
        List<Range<Integer>> sorted = intervals.stream()
                .sorted(Comparator.comparingInt(Range::lowerEndpoint))
                .collect(Collectors.toList());

        // 2. 校验不重叠且连续
        for (int i = 0; i < sorted.size() - 1; i++) {
            Range<Integer> current = sorted.get(i);
            Range<Integer> next = sorted.get(i + 1);
            if (TimeCheckType.OVERLAP.equals(timeCheckType)) {
                // 检查重叠：当前区间的结束 >= 下一个区间的开始
                return current.upperEndpoint() >= next.lowerEndpoint();
            } else if (TimeCheckType.CONTINUOUS.equals(timeCheckType)) {
                // 检查连续：下一个的开始 == 当前的结束 + 1
                return next.lowerEndpoint() != current.upperEndpoint() + 1;
            }

        }

        return false;
    }
}
