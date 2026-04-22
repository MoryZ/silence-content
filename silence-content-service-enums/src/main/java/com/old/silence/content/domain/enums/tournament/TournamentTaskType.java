package com.old.silence.content.domain.enums.tournament;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 赛事任务类型
 *
 * @author EX-ZHANGMENGWEI001
 */
public enum TournamentTaskType implements DescribedEnumValue<Byte> {

    REGISTRATION_END(1, "报名结束"),
    STAGE_SETTLE(2, "场次结算"),
    SEGMENT_SETTLE(3, "片结算"),
    CYCLE_SETTLE(4, "周期结算"),
    ISSUE_REWARD(5, "发放奖励"),
    ;

    private final Byte value;

    private final String description;

    TournamentTaskType(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
