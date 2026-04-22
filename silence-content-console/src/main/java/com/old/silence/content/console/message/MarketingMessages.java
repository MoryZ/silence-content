package com.old.silence.content.console.message;


import com.old.silence.core.context.ErrorCodedEnumMessageSourceResolvable;

public enum MarketingMessages implements ErrorCodedEnumMessageSourceResolvable {


    EVENT_GAME_START_TIME_LESS_THAN_END_TIME( 60),
    EVENT_REGISTRATION_START_TIME_LESS_THAN_END_TIME( 61),
    EVENT_REGISTRATION_TIME_WITHIN_EVENT_TIME( 62),
    TOURNAMENT_GAME_START_TIME_LESS_THAN_END_TIME( 63),
    TOURNAMENT_TIME_WITHIN_EVENT_TIME( 64),
    REGISTRATION_END_TIME_AND_GAME_START_TIME_GAP( 65),
    MIN_REGISTRATION_LESS_THAN_MAX( 66),
    PRIZE_DRAW_START_TIME_LESS_THAN_END_TIME( 67),
    PRIZE_DRAW_TIME_CONTINUOUS( 68),
    PRIZE_DRAW_TIME_OVERLAP( 69),
    DUPLICATE_SPORT_ITEM( 70),
    CHALLENGE_START_TIME_LESS_THAN_END_TIME( 71),
    RANK_START_LESS_THAN_END( 72),
    RANK_INTERVAL_CONTINUOUS( 73),
    RANK_INTERVAL_OVERLAP( 74),
    TEAM_SIZE_EXCEEDS_RANK_MAX( 75),
    TOURNAMENT_RANK_INTERVAL_OVERLAP( 76),
    TOURNAMENT_RANK_INTERVAL_MUST_BE_CONTINUOUS( 77),
    TOURNAMENT_RANKS_MUST_BE_GREATER_THAN_OR_EQUAL_REGISTRATION_LIMIT( 78),
    FORBID_MODIFY_BEFORE_TOURNAMENT_START( 79),
    ;


    private final int errorCode;

    MarketingMessages(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public int getHttpStatusCode() {
        return 0;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}
