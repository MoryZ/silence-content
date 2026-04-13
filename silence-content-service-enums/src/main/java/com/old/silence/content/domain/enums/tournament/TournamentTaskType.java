package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 赛事任务类型
 *
 * @author moryzang
 */
public enum TournamentTaskType implements DescribedEnumValue<Byte> {

    STAGE_SETTLE(1, "场次结算"),
    SEGMENT_SETTLE(2, "片结算"),
    CYCLE_SETTLE(3, "周期结算"),
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
