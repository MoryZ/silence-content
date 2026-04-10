package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 赛事排行榜类型
 *
 * @author moryzang
 */
public enum TournamentRankingType implements DescribedEnumValue<Byte> {

    STAGE_GROUP(1, "场次组内排行"),
    STAGE_NATIONAL(2, "场次全国排行"),
    SEGMENT_GROUP(3, "片-组内排行"),
    SEGMENT_NATIONAL(4, "片-国内排行"),
    CYCLE_GROUP(5, "周期组内排行"),
    CYCLE_NATIONAL(6, "周期全国排行"),
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
