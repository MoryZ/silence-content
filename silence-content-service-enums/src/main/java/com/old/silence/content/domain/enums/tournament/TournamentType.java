package com.old.silence.content.domain.enums.tournament;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 赛事类型
 *
 * @author moryzang
 */
public enum TournamentType implements DescribedEnumValue<Byte> {

    INDIVIDUAL(1, "个人赛"),
    TEAM(2, "团体赛");

    private final Byte value;

    private final String description;

    TournamentType(int value, String description) {
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
