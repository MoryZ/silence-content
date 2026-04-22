package com.old.silence.content.domain.common;


public class TournamentCacheDurationConstant {

    //赛事报名缓存时间（天）
    public static final Long TOURNAMENT_PARTICIPATION_DURATION = 30L;

    //赛事加锁持有时间（秒）
    public static final Long TOURNAMENT_LOCK_TIMEOUT = 60L;

    //赛事详情缓存时间（天）
    public static final Long TOURNAMENT_CONFIG_DURATION = 1L;

    //用户当前组缓存时间（分钟）
    public static final Long TOURNAMENT_PARTICIPANT_GROUP_DURATION = 30L;

    //挑战超时时间
    public static final long TOURNAMENT_ACTIVE_CHALLENGE_DURATION = 30L;

    //全国排行榜缓存超时时间
    public static final long TOURNAMENT_RANK_NATIONAL_DURATION = 30L;

    //小组排行榜缓存超时时间
    public static final long TOURNAMENT_RANK_GROUP_DURATION = 1L;

    //小组名称缓存超时时间
    public static final long TOURNAMENT_GROUP_NAME_DURATION = 1L;

    //挑战分数缓存超时时间
    public static final long TOURNAMENT_CHALLENGE_SCORE_DURATION = 1L;

    //挑战次数缓存超时时间
    public static final long TOURNAMENT_CHALLENGE_COUNT_DURATION = 1L;

    //机器人信息缓存超时时间
    public static final long TOURNAMENT_ROBOT_INFO_DURATION = 30L;

    //用户排行榜记录缓存（分钟）
    public static final Long TOURNAMENT_RANK_PARTICIPANT_RECORD_DURATION = 30L;
}
