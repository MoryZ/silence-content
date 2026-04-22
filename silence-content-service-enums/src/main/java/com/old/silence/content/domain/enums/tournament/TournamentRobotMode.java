package com.old.silence.content.domain.enums.tournament;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 机器人模式
 *
 * @author EX-GUOWEI869
 */
public enum TournamentRobotMode implements DescribedEnumValue<Byte> {


    DISABLED(0, "不启用"),
    ENABLED(1, "启用")
    ;

    private final Byte value;

    private final String description;

    TournamentRobotMode(int value, String description) {
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
