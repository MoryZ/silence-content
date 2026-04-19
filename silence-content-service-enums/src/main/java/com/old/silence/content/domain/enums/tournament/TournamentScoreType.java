package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 赛事排行榜类型
 *
 * @author EX-GUOWEI869
 */
public enum TournamentScoreType implements DescribedEnumValue<Byte> {

    STAGE(1, "场次得分"),
    SEGMENT(2, "片得分"),
    CYCLE(3, "周期得分"),
    TOURNAMENT(4, "赛事得分--总分"),
    ;

    private final Byte value;

    private final String description;

    TournamentScoreType(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }


    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Byte getValue() {
        return value;
    }
}
