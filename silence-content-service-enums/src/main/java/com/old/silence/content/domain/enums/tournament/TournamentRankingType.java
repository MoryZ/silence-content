package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 赛事排行榜类型
 *
 * @author EX-GUOWEI869
 */
public enum TournamentRankingType implements DescribedEnumValue<Byte> {

    GROUP(1, "组内排行"),
    NATIONAL(2, "全国排行"),
    ;

    private final Byte value;

    private final String description;

    TournamentRankingType(int value, String description) {
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
