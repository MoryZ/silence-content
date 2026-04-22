package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 场次类型
 *
 * @author EX-GUOWEI869
 */
public enum TournamentStageType implements DescribedEnumValue<Byte> {
    CYCLE(1, "CYCLE"),

    SESSION(2, "SESSION"),
    SEGMENT(3, "SEGMENT");

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
