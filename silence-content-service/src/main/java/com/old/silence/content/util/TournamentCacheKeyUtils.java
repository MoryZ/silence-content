package com.old.silence.content.util;



import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;

import java.math.BigInteger;

public class TournamentCacheKeyUtils {
    private TournamentCacheKeyUtils() {
    }

    // 赛事报名记录缓存key
    private static final String TOURNAMENT_PARTICIPATION = "tournament:participation:%s:%s:%s";

    // 赛事报名统计缓存key
    private static final String TOURNAMENT_PARTICIPATION_COUNT = "tournament:participationCount:%s";

    // 赛事配置缓存key
    private static final String TOURNAMENT_CONFIG = "tournament:config:%s";

    // 赛事当前挑战缓存key
    private static final String TOURNAMENT_ACTIVE_CHALLENGE = "tournament:active_challenge:%s:%s";

    // 场次挑战次数
    private static final String TOURNAMENT_CHALLENGE_COUNT = "tournament:challengeCount:%s:%s:%s:%s:%s";

    // 赛事组别排名缓存key
    private static final String TOURNAMENT_GROUP_RANK = "tournament:ranking:group:%s:%s";

    // 赛事全国排名缓存key
    private static final String TOURNAMENT_GLOBAL_RANK = "tournament:ranking:national:%s";

    // 赛事小组组名缓存key
    private static final String TOURNAMENT_GROUP_NAME = "tournament:group:name:%s:%s";

    // 赛事小组用户得分缓存
    private static final String TOURNAMENT_CHALLENGE_GROUP_SCORE = "tournament:challenge:score:%s:%s:%s";

    // 赛事全国排名用户得分缓存
    private static final String TOURNAMENT_CHALLENGE_GLOBAL_SCORE = "tournament:challenge:score:%s:%s";

    // 用户当前分组信息
    private static final String TOURNAMENT_PARTICIPANT_GROUP = "tournament:participantGroup:%s:%s:%s:%s:%s";

    // 机器人头像和名称缓存key
    private static final String TOURNAMENT_ROBOT_INFO = "tournament:robot:info:%s";

    private static final String TOURNAMENT_RANK_PARTICIPATION_RECORD = "tournament:rank:participantRecord:%s:%s:%s:%s";

    /**
     * 获取赛事报名记录缓存key
     **/
    public static String getTournamentParticipationKey(BigInteger eventGameId, BigInteger participantId,
            TournamentParticipantType participantType) {
        return String.format(TOURNAMENT_PARTICIPATION, eventGameId, participantId,
                participantType.getValue().intValue());
    }

    /**
     * 获取赛事报名统计缓存key
     **/
    public static String getTournamentParticipationCountKey(BigInteger eventGameId) {
        return String.format(TOURNAMENT_PARTICIPATION_COUNT, eventGameId);
    }

    /**
     * 获取赛事配置key
     **/
    public static String getTournamentConfigKey(BigInteger eventGameId) {
        return String.format(TOURNAMENT_CONFIG, eventGameId);
    }

    /**
     * 获取赛事当前挑战缓存key
     */
    public static String getTournamentActiveChallengeKey(BigInteger eventGameId, BigInteger participantId) {
        return String.format(TOURNAMENT_ACTIVE_CHALLENGE, eventGameId, participantId);
    }

    /**
     * 获取该场次挑战次数
     */
    public static String getTournamentChallengeCountKey(BigInteger eventGameId, BigInteger participantId,
            TournamentParticipantType participantType, int cycleNumber, int segmentNumber, int stageNumber) {
        return String.format(TOURNAMENT_CHALLENGE_COUNT, eventGameId, participantId, participantType.getValue(),
                cycleNumber, segmentNumber, stageNumber);
    }

    /**
     * 获取赛事组别排名缓存key
     */
    public static String getTournamentGroupRankKey(BigInteger eventGameId, BigInteger groupId) {
        return String.format(TOURNAMENT_GROUP_RANK, eventGameId, groupId);
    }

    /**
     * 获取赛事全国排名缓存key
     */
    public static String getTournamentGlobalRankKey(BigInteger eventGameId) {
        return String.format(TOURNAMENT_GLOBAL_RANK, eventGameId);
    }

    /**
     * 获取赛事小组组名缓存key
     */
    public static String getTournamentGroupNameKey(BigInteger eventGameId, BigInteger groupId) {
        return String.format(TOURNAMENT_GROUP_NAME, eventGameId, groupId);
    }

    /**
     * 获取赛事当前组缓存key
     */
    public static String getTournamentParticipantGroupKey(BigInteger eventGameId, BigInteger participantId,
                                                          TournamentParticipantType participantType, TournamentStageType stageType, Integer stageNumber) {
        return String.format(TOURNAMENT_PARTICIPANT_GROUP, eventGameId, participantId, participantType, stageType,
                stageNumber);
    }

    /**
     * 获取赛事小组用户得分缓存key
     */
    public static String getTournamentChallengeGroupScoreKey(BigInteger eventGameId, BigInteger participantId,
            int cycleNumber) {
        return String.format(TOURNAMENT_CHALLENGE_GROUP_SCORE, eventGameId, participantId, cycleNumber);
    }

    /**
     * 获取赛事全国排行用户得分缓存key
     */
    public static String getTournamentChallengeNationalScoreKey(BigInteger eventGameId, BigInteger participantId) {
        return String.format(TOURNAMENT_CHALLENGE_GLOBAL_SCORE, eventGameId, participantId);
    }

    public static String getRobotInfoKey(BigInteger eventGameId, BigInteger robotId) {
        return String.format(TOURNAMENT_ROBOT_INFO, eventGameId, robotId);
    }

    public static String getTournamentRankParticipationRecordKey(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType, TournamentRankingType tournamentRankingType){
        return String.format(TOURNAMENT_RANK_PARTICIPATION_RECORD, eventGameId, participantId, participantType, tournamentRankingType);
    }

}

