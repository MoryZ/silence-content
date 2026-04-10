package com.old.silence.content.domain.enums.tournament;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 场次类型
 *
 * @author moryzang
 */
public enum TournamentStageType implements DescribedEnumValue<Byte> {

    SESSION(1, "SESSION"),
    SEGMENT(2, "SEGMENT");

    private final Byte value;

    private final String description;

    TournamentStageType(int value, String description) {
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
